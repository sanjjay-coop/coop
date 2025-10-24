package org.pf.coop.portal.controller.open.sponsorship;

import java.security.Principal;
import java.util.Calendar;

import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.repository.SponsorshipRepo;
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
@RequestMapping("/open/sponsorship")
public class SponsorshipController extends BaseController {
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "sponsorship";
	}

	@Autowired
	private SponsorshipRepo sponsorshipRepo;
	
	Calendar cal = Calendar.getInstance();
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listSponsorship(@ModelAttribute Sponsorship sponsorship, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("openSearch_sponsorship", sponsorship);
				
			return "redirect:/open/sponsorship/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listSponsorship(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, "lastDate"));
			
			Page<Sponsorship> page;
			
			Sponsorship obj = (Sponsorship) request.getSession().getAttribute("openSearch_sponsorship");
			
			if (obj == null) {
				page = this.sponsorshipRepo.findByPubDateLessThanAndExpDateGreaterThan(cal.getTime(), cal.getTime(), pageable);
				obj = new Sponsorship();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.sponsorshipRepo.findByPubDateLessThanAndExpDateGreaterThan(cal.getTime(), cal.getTime(), pageable);
				} else {
					page = this.sponsorshipRepo.findByPubDateLessThanAndExpDateGreaterThanAndSearchStringContainingIgnoreCase(cal.getTime(), cal.getTime(), obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("openSearch_sponsorship", obj);
			model.addAttribute("sponsorship", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listSponsorship", page.getContent());
			
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
			
			request.getSession().setAttribute("listSponsorshipOpen_pageNumber", pageNumber);
			request.getSession().setAttribute("listSponsorshipOpen_totalPages", totalPages);
			
			return "open/sponsorship/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listSponsorship(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listSponsorshipOpen_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listSponsorshipOpen_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/open/sponsorship/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "lastDate"));
			
			Page<Sponsorship> page;
			
			Sponsorship obj = (Sponsorship) request.getSession().getAttribute("openSearch_sponsorship");
			
			if (obj == null) {
				page = this.sponsorshipRepo.findByPubDateLessThanAndExpDateGreaterThan(cal.getTime(), cal.getTime(), pageable);
				obj = new Sponsorship();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.sponsorshipRepo.findByPubDateLessThanAndExpDateGreaterThan(cal.getTime(), cal.getTime(), pageable);
				} else {
					page = this.sponsorshipRepo.findByPubDateLessThanAndExpDateGreaterThanAndSearchStringContainingIgnoreCase(cal.getTime(), cal.getTime(), obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("openSearch_sponsorship", obj);
			model.addAttribute("sponsorship", obj);
			
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
			
			request.getSession().setAttribute("listSponsorshipOpen_pageNumber", pageNumber);
			request.getSession().setAttribute("listSponsorshipOpen_totalPages", totalPages);
			
			model.addAttribute("listSponsorship", page.getContent());
			
			return "open/sponsorship/list";
		
		} catch(Exception e) {
			return "redirect:/open/sponsorship/list";
		}
	}
}
