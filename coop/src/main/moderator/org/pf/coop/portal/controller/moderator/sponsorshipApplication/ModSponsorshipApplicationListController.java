package org.pf.coop.portal.controller.moderator.sponsorshipApplication;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.repository.SponsorshipApplicationRepo;
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
@RequestMapping("/moderator/sponsorshipApplication")
public class ModSponsorshipApplicationListController extends ModeratorBaseController {
	
	@Autowired
	private SponsorshipApplicationRepo sponsorshipApplicationRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listSponsorshipApplication(@ModelAttribute SponsorshipApplication sponsorshipApplication, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_sponsorshipApplication", sponsorshipApplication);
				
			return "redirect:/moderator/sponsorshipApplication/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listSponsorshipApplication(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<SponsorshipApplication> page;
			
			SponsorshipApplication obj = (SponsorshipApplication) request.getSession().getAttribute("moderatorSearch_sponsorshipApplication");
			
			if (obj == null) {
				page = this.sponsorshipApplicationRepo.findAll(pageable);
				obj = new SponsorshipApplication();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.sponsorshipApplicationRepo.findAll(pageable);
				} else {
					page = this.sponsorshipApplicationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_sponsorshipApplication", obj);
			model.addAttribute("sponsorshipApplication", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listSponsorshipApplication", page.getContent());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listSponsorshipApplicationModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listSponsorshipApplicationModerator_totalPages", totalPages);
			
			return "moderator/sponsorshipApplication/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listSponsorshipApplication(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listSponsorshipApplicationModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listSponsorshipApplicationModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/sponsorshipApplication/list";
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
			
			Page<SponsorshipApplication> page;
			
			SponsorshipApplication obj = (SponsorshipApplication) request.getSession().getAttribute("moderatorSearch_sponsorshipApplication");
			
			if (obj == null) {
				page = this.sponsorshipApplicationRepo.findAll(pageable);
				obj = new SponsorshipApplication();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.sponsorshipApplicationRepo.findAll(pageable);
				} else {
					page = this.sponsorshipApplicationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_sponsorshipApplication", obj);
			model.addAttribute("sponsorshipApplication", obj);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listSponsorshipApplicationModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listSponsorshipApplicationModerator_totalPages", totalPages);
			
			model.addAttribute("listSponsorshipApplication", page.getContent());
			
			return "moderator/sponsorshipApplication/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/sponsorshipApplication/list";
		}
	}
}
