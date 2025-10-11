package org.pf.coop.portal.controller.moderator.newsFeed;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.NewsFeed;
import org.pf.coop.portal.repository.NewsFeedRepo;
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
@RequestMapping("/moderator/newsFeed")
public class ModNewsFeedListController extends ModeratorBaseController {
	
	@Autowired
	private NewsFeedRepo newsFeedRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listNewsFeed(@ModelAttribute NewsFeed newsFeed, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_newsFeed", newsFeed);
				
			return "redirect:/moderator/newsFeed/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listNewsFeed(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<NewsFeed> page;
			
			NewsFeed obj = (NewsFeed) request.getSession().getAttribute("moderatorSearch_newsFeed");
			
			if (obj == null) {
				page = this.newsFeedRepo.findAll(pageable);
				obj = new NewsFeed();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.newsFeedRepo.findAll(pageable);
				} else {
					page = this.newsFeedRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_newsFeed", obj);
			model.addAttribute("newsFeed", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listNewsFeed", page.getContent());
			
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
			
			request.getSession().setAttribute("listNewsFeedModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listNewsFeedModerator_totalPages", totalPages);
			
			return "moderator/newsFeed/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listNewsFeed(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listNewsFeedModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listNewsFeedModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/newsFeed/list";
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
			
			Page<NewsFeed> page;
			
			NewsFeed obj = (NewsFeed) request.getSession().getAttribute("moderatorSearch_newsFeed");
			
			if (obj == null) {
				page = this.newsFeedRepo.findAll(pageable);
				obj = new NewsFeed();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.newsFeedRepo.findAll(pageable);
				} else {
					page = this.newsFeedRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_newsFeed", obj);
			model.addAttribute("newsFeed", obj);
			
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
			
			request.getSession().setAttribute("listNewsFeedModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listNewsFeedModerator_totalPages", totalPages);
			
			model.addAttribute("listNewsFeed", page.getContent());
			
			return "moderator/newsFeed/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/newsFeed/list";
		}
	}
}


