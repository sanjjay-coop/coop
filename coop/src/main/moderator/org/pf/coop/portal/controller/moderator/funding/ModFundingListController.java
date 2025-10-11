package org.pf.coop.portal.controller.moderator.funding;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.repository.FundingRepo;
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
@RequestMapping("/moderator/funding")
public class ModFundingListController extends ModeratorBaseController {
	
	@Autowired
	private FundingRepo fundingRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listFunding(@ModelAttribute Funding funding, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_funding", funding);
				
			return "redirect:/moderator/funding/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listFunding(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Funding> page;
			
			Funding obj = (Funding) request.getSession().getAttribute("moderatorSearch_funding");
			
			if (obj == null) {
				page = this.fundingRepo.findAll(pageable);
				obj = new Funding();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.fundingRepo.findAll(pageable);
				} else {
					page = this.fundingRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_funding", obj);
			model.addAttribute("funding", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listFunding", page.getContent());
			
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
			
			request.getSession().setAttribute("listFundingModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listFundingModerator_totalPages", totalPages);
			
			return "moderator/funding/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listFunding(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listFundingModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listFundingModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/funding/list";
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
			
			Page<Funding> page;
			
			Funding obj = (Funding) request.getSession().getAttribute("moderatorSearch_funding");
			
			if (obj == null) {
				page = this.fundingRepo.findAll(pageable);
				obj = new Funding();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.fundingRepo.findAll(pageable);
				} else {
					page = this.fundingRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_funding", obj);
			model.addAttribute("funding", obj);
			
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
			
			request.getSession().setAttribute("listFundingModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listFundingModerator_totalPages", totalPages);
			
			model.addAttribute("listFunding", page.getContent());
			
			return "moderator/funding/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/funding/list";
		}
	}
}
