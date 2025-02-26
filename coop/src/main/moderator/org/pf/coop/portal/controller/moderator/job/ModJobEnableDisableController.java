package org.pf.coop.portal.controller.moderator.job;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModJobEnableDisableController extends ModeratorBaseController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping("/moderator/job/enable/{id}")
	public String enableRecord(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.jobService.enableDisableJob(id, true, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated. Please try again later.");
				return "home/job/addNew";
			} else {
				reat.addFlashAttribute("message", "Record updated successfully.");
			}
			
			return "redirect:/moderator/job/view/" + id;
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/job/list/current";
		}
	}

	@GetMapping("/moderator/job/disable/{id}")
	public String disableRecord(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			
			TransactionResult tr = this.jobService.enableDisableJob(id, false, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated. Please try again later.");
				return "home/job/addNew";
			} else {
				reat.addFlashAttribute("message", "Record updated successfully.");
			}
			
			return "redirect:/moderator/job/view/" + id;
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/job/list/current";
		}
	}
}
