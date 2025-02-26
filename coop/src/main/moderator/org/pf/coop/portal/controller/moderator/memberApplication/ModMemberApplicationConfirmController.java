package org.pf.coop.portal.controller.moderator.memberApplication;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.service.MemberApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/memberApplication/confirm")
public class ModMemberApplicationConfirmController extends ModeratorBaseController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private MemberApplicationService memberApplicationService;
	
	@GetMapping("/{id}")
	public String confirm(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			MemberApplication obj = (MemberApplication) this.memberApplicationService.getById(id);
			
			if (obj == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/memberApplication/list";
			}
			
			if (obj.getStatus()==true) {
				reat.addFlashAttribute("message", "Error! Member details are already added.");
				return "redirect:/moderator/memberApplication/view/" + id;
			}
			
			TransactionResult tr = this.memberApplicationService.confirmMemberApplication(obj, principal.getName());
	
			if (tr == null) {
				reat.addFlashAttribute("message", "Error! Contact admin for resolution.");
				return "redirect:/moderator/memberApplication/list";
			} else {
				if (tr.isStatus()) {
					
					Member member = (Member) tr.getObj();
					
					MessageText mt = messageTextRepo.findByMessageFor("WELCOME");
					
					this.emailService.sendEmail(member.getEmail(), mt.getSubject(), mt.getMessage(member));
					
					reat.addFlashAttribute("message", "Sucess! " + "Member added successfully.");
					return "redirect:/moderator/memberApplication/view/" + id;
				} else {
					reat.addFlashAttribute("message", "Error! " + tr.getMessage());
					return "redirect:/moderator/memberApplication/view/" + id;
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/memberApplication/list";
		}
	}
}