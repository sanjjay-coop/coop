package org.pf.coop.portal.controller.moderator.invitation;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/invitation/delete")
public class ModInvitationDeleteController extends ModeratorBaseController {

	@Autowired
	private InvitationService invitationService;
	
	@GetMapping("/{id}")
	public String deleteInvitation(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.invitationService.deleteInvitation(id, principal.getName());
			
			if (tr!=null) {
				if (tr.isStatus()) {

					reat.addFlashAttribute("message", "Record deleted successfully.");
				} else {
	
					reat.addFlashAttribute("message", "Record not deleted.");
				}
			} else {

				reat.addFlashAttribute("message", "Record not found.");
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
		}
		return "redirect:/moderator/invitation/list/current";
	}
}
