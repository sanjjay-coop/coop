package org.pf.coop.portal.controller.moderator.organization;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Organization;
import org.pf.coop.portal.repository.OrganizationRepo;
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
@RequestMapping("/moderator/organization")
public class OrganizationListController extends ModeratorBaseController {
	
	@Autowired
	private OrganizationRepo organizationRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listOrganization(@ModelAttribute Organization organization, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_organization", organization);
				
			return "redirect:/moderator/organization/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listOrganization(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Organization> page;
			
			Organization obj = (Organization) request.getSession().getAttribute("moderatorSearch_organization");
			
			if (obj == null) {
				page = this.organizationRepo.findAll(pageable);
				obj = new Organization();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.organizationRepo.findAll(pageable);
				} else {
					page = this.organizationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_organization", obj);
			model.addAttribute("organization", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listOrganization", page.getContent());
			
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
			
			request.getSession().setAttribute("listOrganizationModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listOrganizationModerator_totalPages", totalPages);
			
			return "moderator/organization/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listOrganization(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listOrganizationModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listOrganizationModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/organization/list";
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
			
			Page<Organization> page;
			
			Organization obj = (Organization) request.getSession().getAttribute("moderatorSearch_organization");
			
			if (obj == null) {
				page = this.organizationRepo.findAll(pageable);
				obj = new Organization();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.organizationRepo.findAll(pageable);
				} else {
					page = this.organizationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_organization", obj);
			model.addAttribute("organization", obj);
			
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
			
			request.getSession().setAttribute("listOrganizationModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listOrganizationModerator_totalPages", totalPages);
			
			model.addAttribute("listOrganization", page.getContent());
			
			return "moderator/organization/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/organization/list";
		}
	}
}
