package org.pf.coop.portal.controller.home.invitation;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.service.InvitationService;
import org.pf.coop.portal.validators.InvitationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/home/invitation/addNew")
public class InvitationAddController extends HomeBaseController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private InvitationValidator invitationValidator;
	
	@GetMapping
	public String invitationAdd(Model model) {
		
		Invitation invitation = new Invitation();
		
		model.addAttribute(invitation);
		
		return "home/invitation/addNew";
	}
	
	@PostMapping
	public String invitationAdd(@ModelAttribute Invitation invitation,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		invitation.setMember(member);
		
		this.invitationValidator.validate(invitation, result);
		
		if (result.hasErrors()) {
			return "home/invitation/addNew";
		}
		
		try {
			TransactionResult tr = this.invitationService.addInvitation(invitation, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/home/invitation/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				
				MessageText mt = messageTextRepo.findByMessageFor("INVITATION");
				
				if (mt!= null) this.emailService.sendEmail(invitation.getEmail(), mt.getSubject(), mt.getMessage(invitation, this.getParameters().getSiteUrl()));

				return "redirect:/home/invitation/list";
				
			} else {
				reat.addFlashAttribute("message", tr.getMessage());
				return "redirect:/home/invitation/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home/invitation/list/current";
		}
	}
}
