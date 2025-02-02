package org.pf.coop.portal.controller.home.job;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.JobRepo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/job/edit")
public class JobEditController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobRepo jobRepo;

	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private JobValidator jobValidator;
	
	@ModelAttribute("listEducationLevel")
	public List<EducationLevel> getListEducationLevel(){
		return (List<EducationLevel>) this.educationLevelRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/{id}")
	public String editJob(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Job job = this.jobRepo.getReferenceById(id);
			
			if (job == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/job/addNew";
			}
			
			if (!job.getOwner().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/home";
			}
	
			model.addAttribute("job", job);
			
			return "home/job/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/job/addNew";
		}
	}

	@PostMapping("/*")
	public String editJob(@ModelAttribute Job job,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.jobValidator.validate(job, result);
		
		if (result.hasErrors()) {
			return "home/job/edit";
		}
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Job emp = this.jobRepo.getReferenceById(job.getId());
			

			if (!emp.getOwner().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/home";
			}
			
			TransactionResult tr = this.jobService.updateJob(job, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not editd.");
				return "redirect:/home/job/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record editd successfully.");
					return "redirect:/home/job/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/home/job/edit/"+job.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home/job/edit/"+job.getId();
		}
	}
}
