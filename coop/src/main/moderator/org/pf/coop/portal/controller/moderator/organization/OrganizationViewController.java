package org.pf.coop.portal.controller.moderator.organization;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Organization;
import org.pf.coop.portal.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrganizationViewController extends ModeratorBaseController {

	@Autowired
	private OrganizationService organizationService;
	
	@GetMapping("/moderator/organization/view/{id}")
	public String organizationView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			Organization organization = (Organization) this.organizationService.getById(id);
			
			if (organization == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/organization/list/current";
			}
	
			model.addAttribute("organization", organization);
			
			return "moderator/organization/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/organization/list";
		}
	}
}
