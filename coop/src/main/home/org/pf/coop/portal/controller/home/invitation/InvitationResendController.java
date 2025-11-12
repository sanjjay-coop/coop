package org.pf.coop.portal.controller.home.invitation;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.InvitationRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/invitation/resend")
public class InvitationResendController extends HomeBaseController {

	@Autowired
	private InvitationRepo invitationRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private InvitationService invitationService;
	
	@GetMapping("/{id}")
	public String resendInvitation(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Invitation invitation = this.invitationRepo.findByIdAndMember(id, member);
			
			if (invitation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/invitation/list/current";
			}
			
			if (invitation.getStatus()!=null && invitation.getStatus().booleanValue()==true) {
				reat.addFlashAttribute("message", "Invitation is already accepted.");
				return "redirect:/home/invitation/list/current";
			}
			
			if (invitation.isResendPossible()) {
				
				MessageText mt = messageTextRepo.findByMessageFor("INVITATION");
				
				if (mt != null) this.emailService.sendEmail(invitation.getEmail(), mt.getSubject(), mt.getMessage(invitation, this.getParameters().getSiteUrl()));
				
				try {
					this.invitationService.updateInvitation(invitation, principal.getName());
					return "redirect:/home/invitation/list/current";
		
				} catch (Exception e) {
					reat.addFlashAttribute("message", e.getMessage());
					return "redirect:/home/invitation/list/current";
				}
			} else {
				reat.addFlashAttribute("message", "Revised-invitation can be sent only after lapse of 15 days from previous invitation.");
				return "redirect:/home/invitation/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/invitation/list/current";
		}
	}
}