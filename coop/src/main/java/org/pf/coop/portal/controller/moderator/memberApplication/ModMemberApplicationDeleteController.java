package org.pf.coop.portal.controller.moderator.memberApplication;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.MemberApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/memberApplication/delete")
public class ModMemberApplicationDeleteController extends ModeratorBaseController {

	@Autowired
	private MemberApplicationService memberApplicationService;
	
	@GetMapping("/{id}")
	public String deleteMemberApplication(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.memberApplicationService.deleteMemberApplication(id, principal.getName());
			
			if (tr!=null) {
				if (tr.isStatus()) {

					reat.addFlashAttribute("message", "Record deleted successfully.");
					return "redirect:/moderator/memberApplication/list";
				} else {
	
					reat.addFlashAttribute("message", "Record not deleted.");
					return "redirect:/moderator/memberApplication/view/" + id;
				}
			} else {

				reat.addFlashAttribute("message", "Record not found.");
				return "redirect:/moderator/memberApplication/list";
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/memberApplication/list";
		}
	}
}