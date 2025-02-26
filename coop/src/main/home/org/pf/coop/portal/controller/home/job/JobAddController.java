package org.pf.coop.portal.controller.home.job;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.JobService;
import org.pf.coop.portal.validators.JobValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/home/job/addNew")
public class JobAddController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private JobService jobService;

	@Autowired
	private EducationLevelRepo educationLevelRepo;

	@Autowired
	private JobValidator jobValidator;
	
	@ModelAttribute("listEducationLevel")
	public List<EducationLevel> getListEducationLevel(){
		return (List<EducationLevel>) this.educationLevelRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String jobAdd(Model model) {
		
		Job job = new Job();
		
		model.addAttribute(job);
		
		return "home/job/addNew";
	}
	
	@PostMapping
	public String jobAdd(@ModelAttribute Job job,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		job.setOwner(member);
		
		this.jobValidator.validate(job, result);
		
		if (result.hasErrors()) {
			return "home/job/addNew";
		}
		
		try {
			TransactionResult tr = this.jobService.addJob(job, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "home/job/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/home/job/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "home/job/addNew";
		}
	}
}
