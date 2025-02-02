package org.pf.coop.portal.controller.moderator.matrimonial;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.service.MatrimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModMatrimonialViewController extends ModeratorBaseController {

	@Autowired
	private MatrimonialService matrimonialService;
	
	@GetMapping("/moderator/matrimonial/view/{id}")
	public String matrimonialView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			Matrimonial matrimonial = (Matrimonial) this.matrimonialService.getById(id);
			
			if (matrimonial == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/matrimonial/list";
			}
	
			model.addAttribute("matrimonial", matrimonial);
			
			return "moderator/matrimonial/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/matrimonial/list";
		}
	}
}