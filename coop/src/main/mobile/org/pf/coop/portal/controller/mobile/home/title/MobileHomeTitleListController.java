package org.pf.coop.portal.controller.mobile.home.title;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.library.TitleRepo;
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
@RequestMapping("/mobile/home/title")
public class MobileHomeTitleListController extends MobileBaseController {

	@Autowired
	private TitleRepo titleRepo;
	
	private int resultSize = 10;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listTitle(@ModelAttribute Title title, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("mobileHomeSearch_title", title);
				
			return "redirect:/mobile/home/title/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/home";
		}
	}
	
	@GetMapping("/list")
	public String listTitle(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, resultSize, Sort.by(Sort.Direction.ASC, "uniformTitle"));
			
			Page<Title> page;
			
			Title obj = (Title) request.getSession().getAttribute("mobileHomeSearch_title");
			
			if (obj == null) {
				page = this.titleRepo.findAll(pageable);
				obj = new Title();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.titleRepo.findAll(pageable);
				} else {
					page = this.titleRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("mobileHomeSearch_title", obj);
			model.addAttribute("title", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listTitle", page.getContent());
			
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
			
			request.getSession().setAttribute("listTitleMobileHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listTitleMobileHome_totalPages", totalPages);
			
			return "mobile/home/title/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listTitle(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listTitleMobileHome_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listTitleMobileHome_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/title/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, resultSize, Sort.by(Sort.Direction.ASC, "uniformTitle"));
			
			Page<Title> page;
			
			Title obj = (Title) request.getSession().getAttribute("mobileHomeSearch_title");
			
			if (obj == null) {
				page = this.titleRepo.findAll(pageable);
				obj = new Title();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.titleRepo.findAll(pageable);
				} else {
					page = this.titleRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("mobileHomeSearch_title", obj);
			model.addAttribute("title", obj);
			
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
			
			request.getSession().setAttribute("listTitleMobileHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listTitleMobileHome_totalPages", totalPages);
			
			model.addAttribute("listTitle", page.getContent());
			
			return "mobile/home/title/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/title/list";
		}
	}
}
