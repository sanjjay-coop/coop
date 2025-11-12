package org.pf.coop.portal.controller.moderator.business;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModBusinessEnableDisableController extends ModeratorBaseController {
	
	@Autowired
	private BusinessService businessService;
	
	@GetMapping("/moderator/business/enable/{id}")
	public String enableRecord(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.businessService.enableDisableBusiness(id, true, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated. Please try again later.");
				return "redirect:/moderator/business/view/" + id;
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record updated successfully.");
				return "redirect:/moderator/business/view/" + id;
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/business/view/" + id;
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/business/view/" + id;
		}
	}

	@GetMapping("/moderator/business/disable/{id}")
	public String disableRecord(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.businessService.enableDisableBusiness(id, false, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated. Please try again later.");
				return "redirect:/moderator/business/view/" + id;
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record updated successfully.");
				return "redirect:/moderator/business/view/" + id;
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/business/view/" + id;
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/business/view/" + id;
		}
	}
}
