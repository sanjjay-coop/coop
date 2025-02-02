package org.pf.coop.portal.controller.moderator.memberApplication;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.service.MemberApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/memberApplication/view")
public class ModMemberApplicationViewController extends ModeratorBaseController {

	@Autowired
	private MemberApplicationService memberApplicationService;
	
	@GetMapping("/{id}")
	public String viewMemberApplication(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			MemberApplication ma = (MemberApplication) this.memberApplicationService.getById(id);
			
			if (ma == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/memberApplication/list";
			}
	
			model.addAttribute("memberApplication", ma);
			
			return "moderator/memberApplication/view";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/memberApplication/list";
		}
	}
}
