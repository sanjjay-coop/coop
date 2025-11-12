package org.pf.coop.portal.controller.home.job;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JobViewController extends HomeBaseController {

	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/home/job/view/{id}")
	public String jobView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			Job job = this.jobRepo.findByIdAndOwner(id, this.memberRepo.findByMemIdIgnoreCase(principal.getName()));
			
			if (job == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/job/list/current";
			}
	
			model.addAttribute("job", job);
			
			return "home/job/view";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/job/list/current";
		}
	}
}