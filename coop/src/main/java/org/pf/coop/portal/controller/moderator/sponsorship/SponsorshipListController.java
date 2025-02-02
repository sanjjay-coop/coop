package org.pf.coop.portal.controller.moderator.sponsorship;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/moderator/sponsorship")
public class SponsorshipListController extends ModeratorBaseController {
	
	@Autowired
	private SponsorshipRepo sponsorshipRepo;
	
	@GetMapping("/list")
	public String listSponsorship(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "pubDate"));
		
		Page<Sponsorship> page = this.sponsorshipRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listSponsorship", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listSponsorship_pageNumber", pageNumber);
		request.getSession().setAttribute("listSponsorship_totalPages", totalPages);
		
		return "moderator/sponsorship/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listSponsorship(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listSponsorship_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listSponsorship_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/sponsorship/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "pubDate"));
			
			Page<Sponsorship> page = this.sponsorshipRepo.findAll(pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listSponsorship_pageNumber", pageNumber);
			request.getSession().setAttribute("listSponsorship_totalPages", totalPages);
			
			model.addAttribute("listSponsorship", page.getContent());
			
			return "moderator/sponsorship/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/sponsorship/list";
		}
	}
}

