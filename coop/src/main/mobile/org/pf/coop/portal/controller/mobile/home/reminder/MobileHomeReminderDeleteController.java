package org.pf.coop.portal.controller.mobile.home.reminder;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/home/reminder/delete")
public class MobileHomeReminderDeleteController extends MobileBaseController {

	@Autowired
	private ReminderService reminderService;

	@GetMapping("/{id}")
	public String deleteReminder(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.reminderService.deleteReminder(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/mobile/home/reminder/list/current";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/mobile/home/reminder/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/mobile/home/reminder/list/current";
			}

		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/mobile/home/reminder/list/current";
			
		}
	}
}
