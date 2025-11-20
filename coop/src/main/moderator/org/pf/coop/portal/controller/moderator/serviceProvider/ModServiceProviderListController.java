package org.pf.coop.portal.controller.moderator.serviceProvider;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.ServiceProviderRepo;
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
@RequestMapping("/moderator/serviceProvider")
public class ModServiceProviderListController extends ModeratorBaseController {
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listServiceProvider(@ModelAttribute ServiceProvider serviceProvider, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_serviceProvider", serviceProvider);
				
			return "redirect:/moderator/serviceProvider/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listServiceProvider(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<ServiceProvider> page;
			
			ServiceProvider obj = (ServiceProvider) request.getSession().getAttribute("moderatorSearch_serviceProvider");
			
			if (obj == null) {
				page = this.serviceProviderRepo.findAll(pageable);
				obj = new ServiceProvider();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.serviceProviderRepo.findAll(pageable);
				} else {
					page = this.serviceProviderRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_serviceProvider", obj);
			model.addAttribute("serviceProvider", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("totalRecords", page.getTotalElements());
			
			model.addAttribute("listServiceProvider", page.getContent());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listServiceProviderModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listServiceProviderModerator_totalPages", totalPages);
			
			return "moderator/serviceProvider/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listServiceProvider(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listServiceProviderModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listServiceProviderModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/serviceProvider/list";
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
			
			Page<ServiceProvider> page;
			
			ServiceProvider obj = (ServiceProvider) request.getSession().getAttribute("moderatorSearch_serviceProvider");
			
			if (obj == null) {
				page = this.serviceProviderRepo.findAll(pageable);
				obj = new ServiceProvider();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.serviceProviderRepo.findAll(pageable);
				} else {
					page = this.serviceProviderRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_serviceProvider", obj);
			model.addAttribute("serviceProvider", obj);
			
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
			
			request.getSession().setAttribute("listServiceProviderModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listServiceProviderModerator_totalPages", totalPages);
			
			model.addAttribute("listServiceProvider", page.getContent());
			
			return "moderator/serviceProvider/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/serviceProvider/list";
		}
	}
}