package org.pf.coop.portal.controller.home.job;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home/job/all")
public class JobListAllController extends HomeBaseController {
	
	@Autowired
	private JobRepo jobRepo;
	
	@GetMapping("/list")
	public String listJob(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<Job> page = this.jobRepo.findByEnabled(true, pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listJobAll", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listJobAll_pageNumber", pageNumber);
		request.getSession().setAttribute("listJobAll_totalPages", totalPages);
		
		return "home/job/listAll";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listJob(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listJobAll_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listJobAll_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/job/all/list";
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
			
			Page<Job> page = this.jobRepo.findByEnabled(true, pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listJobAll_pageNumber", pageNumber);
			request.getSession().setAttribute("listJobAll_totalPages", totalPages);
			
			model.addAttribute("listJobAll", page.getContent());
			
			return "home/job/listAll";
		
		} catch(Exception e) {
			return "redirect:/home/job/all/list";
		}
	}
}
