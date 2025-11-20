package org.pf.coop.portal.controller.moderator.serviceProvider;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModServiceProviderViewController extends ModeratorBaseController {

	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@GetMapping("/moderator/serviceProvider/view/{id}")
	public String serviceProviderView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			ServiceProvider serviceProvider = (ServiceProvider) this.serviceProviderService.getById(id);
			
			if (serviceProvider == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/serviceProvider/list/current";
			}
	
			model.addAttribute("serviceProvider", serviceProvider);
			
			return "moderator/serviceProvider/view";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/serviceProvider/list/current";
		}
	}
}
