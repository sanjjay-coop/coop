package org.pf.coop.portal.controller.moderator.job;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModJobViewController extends ModeratorBaseController {

	@Autowired
	private JobService jobService;
	
	@GetMapping("/moderator/job/view/{id}")
	public String jobView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			Job job = (Job) this.jobService.getById(id);
			
			if (job == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/job/list";
			}
	
			model.addAttribute("job", job);
			
			return "moderator/job/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/job/list";
		}
	}
}