package org.pf.coop.portal.controller.moderator.business;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModBusinessViewController extends ModeratorBaseController {

	@Autowired
	private BusinessService businessService;
	
	@GetMapping("/moderator/business/view/{id}")
	public String businessView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			Business business = (Business) this.businessService.getById(id);
			
			if (business == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/business/list/current";
			}
	
			model.addAttribute("business", business);
			
			return "moderator/business/view";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/business/list/current";
		}
	}
}
