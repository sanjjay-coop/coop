package org.pf.coop.portal.controller.moderator.memberApplication;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.repository.MemberApplicationRepo;
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
@RequestMapping("/moderator/memberApplication")
public class ModMemberApplicationListController extends ModeratorBaseController {
	
	@Autowired
	private MemberApplicationRepo memberApplicationRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listMemberApplication(@ModelAttribute MemberApplication memberApplication, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_memberApplication", memberApplication);
				
			return "redirect:/moderator/memberApplication/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listMemberApplication(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<MemberApplication> page;
			
			MemberApplication obj = (MemberApplication) request.getSession().getAttribute("moderatorSearch_memberApplication");
			
			if (obj == null) {
				page = this.memberApplicationRepo.findAll(pageable);
				obj = new MemberApplication();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.memberApplicationRepo.findAll(pageable);
				} else {
					page = this.memberApplicationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_memberApplication", obj);
			model.addAttribute("memberApplication", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listMemberApplication", page.getContent());
			
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
			
			request.getSession().setAttribute("listMemberApplicationModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listMemberApplicationModerator_totalPages", totalPages);
			
			return "moderator/memberApplication/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMemberApplication(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listMemberApplicationModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listMemberApplicationModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/memberApplication/list";
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
			
			Page<MemberApplication> page;
			
			MemberApplication obj = (MemberApplication) request.getSession().getAttribute("moderatorSearch_memberApplication");
			
			if (obj == null) {
				page = this.memberApplicationRepo.findAll(pageable);
				obj = new MemberApplication();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.memberApplicationRepo.findAll(pageable);
				} else {
					page = this.memberApplicationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_memberApplication", obj);
			model.addAttribute("memberApplication", obj);
			
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
			
			request.getSession().setAttribute("listMemberApplicationModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listMemberApplicationModerator_totalPages", totalPages);
			
			model.addAttribute("listMemberApplication", page.getContent());
			
			return "moderator/memberApplication/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/memberApplication/list";
		}
	}
}
