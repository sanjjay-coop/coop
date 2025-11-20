package org.pf.coop.portal.controller.moderator.serviceProvider;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModServiceProviderEnableDisableController extends ModeratorBaseController {
	
	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@GetMapping("/moderator/serviceProvider/enable/{id}")
	public String enableRecord(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.serviceProviderService.enableDisableServiceProvider(id, true, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated. Please try again later.");
				return "redirect:/moderator/serviceProvider/view/" + id;
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record updated successfully.");
				return "redirect:/moderator/serviceProvider/view/" + id;
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/serviceProvider/view/" + id;
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/serviceProvider/view/" + id;
		}
	}

	@GetMapping("/moderator/serviceProvider/disable/{id}")
	public String disableRecord(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.serviceProviderService.enableDisableServiceProvider(id, false, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated. Please try again later.");
				return "redirect:/moderator/serviceProvider/view/" + id;
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record updated successfully.");
				return "redirect:/moderator/serviceProvider/view/" + id;
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/serviceProvider/view/" + id;
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/serviceProvider/view/" + id;
		}
	}
}
