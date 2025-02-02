package org.pf.coop.portal.controller.moderator.business;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.repository.BusinessRepo;
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
@RequestMapping("/moderator/business")
public class ModBusinessListController extends ModeratorBaseController {
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@GetMapping("/list")
	public String listModBusiness(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<Business> page = this.businessRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listBusiness", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listModBusiness_pageNumber", pageNumber);
		request.getSession().setAttribute("listModBusiness_totalPages", totalPages);
		
		return "moderator/business/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listModBusiness(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listModBusiness_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listModBusiness_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/business/list";
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
			
			Page<Business> page = this.businessRepo.findAll(pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listModBusiness_pageNumber", pageNumber);
			request.getSession().setAttribute("listModBusiness_totalPages", totalPages);
			
			model.addAttribute("listBusiness", page.getContent());
			
			return "moderator/business/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/business/list";
		}
	}
}

