package org.pf.coop.portal.controller.home.job;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/job/delete")
public class JobDeleteController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private JobService jobService;

	@GetMapping("/{id}")
	public String deleteJob(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Job job = this.jobRepo.findByIdAndOwner(id, member);
			
			if (job == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/job/list/current";
			}
			
			TransactionResult tr = this.jobService.deleteJob(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/home/job/list/current";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/home/job/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/home/job/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/home/job/list/current";
			
		}
	}
}