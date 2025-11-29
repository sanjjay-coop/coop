package org.pf.coop.portal.controller.mobile.home.job;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mobile/home/job/all")
public class MobileHomeJobListController extends MobileBaseController {
	
	@Autowired
	private JobRepo jobRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listJob(@ModelAttribute Job job, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("Search_job_all_home", job);
				
			return "redirect:/home/job/all/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listJob(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Job> page;
			
			Job obj = (Job) request.getSession().getAttribute("Search_job_all_home");
			
			if (obj == null) {
				page = this.jobRepo.findByEnabled(true, pageable);
				obj = new Job();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.jobRepo.findByEnabled(true, pageable);
				} else {
					page = this.jobRepo.findByEnabledAndSearchStringContainingIgnoreCase(true, obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("Search_job_all_home", obj);
			model.addAttribute("job", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listJobAll", page.getContent());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listAllJobHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listAllJobHome_totalPages", totalPages);
			
			return "mobile/home/job/listAll";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listJob(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listAllJobHome_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listAllJobHome_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/job/all/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Job> page;
			
			Job obj = (Job) request.getSession().getAttribute("Search_job_all_home");
			
			if (obj == null) {
				page = this.jobRepo.findByEnabled(true, pageable);
				obj = new Job();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.jobRepo.findByEnabled(true, pageable);
				} else {
					page = this.jobRepo.findByEnabledAndSearchStringContainingIgnoreCase(true, obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("Search_job_all_home", obj);
			model.addAttribute("job", obj);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listAllJobHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listAllJobHome_totalPages", totalPages);
			
			model.addAttribute("listJobAll", page.getContent());
			
			return "mobile/home/job/listAll";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/job/all/list";
		}
	}

}
