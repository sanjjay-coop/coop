package org.pf.coop.portal.controller.manager.memberGroup;

import java.security.Principal;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.repository.MemberGroupRepo;
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
@RequestMapping("/manager/memberGroup")
public class MemberGroupListController extends ManagerBaseController {
	
	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listMemberGroup(@ModelAttribute MemberGroup memberGroup, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("managerSearch_memberGroup", memberGroup);
				
			return "redirect:/manager/memberGroup/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listMemberGroup(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<MemberGroup> page;
			
			MemberGroup obj = (MemberGroup) request.getSession().getAttribute("managerSearch_memberGroup");
			
			if (obj == null) {
				page = this.memberGroupRepo.findAll(pageable);
				obj = new MemberGroup();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.memberGroupRepo.findAll(pageable);
				} else {
					page = this.memberGroupRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("managerSearch_memberGroup", obj);
			model.addAttribute("memberGroup", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listMemberGroup", page.getContent());
			
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
			
			request.getSession().setAttribute("listMemberGroupManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listMemberGroupManager_totalPages", totalPages);
			
			return "manager/memberGroup/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMemberGroup(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listMemberGroupManager_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listMemberGroupManager_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/memberGroup/list";
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
			
			Page<MemberGroup> page;
			
			MemberGroup obj = (MemberGroup) request.getSession().getAttribute("managerSearch_memberGroup");
			
			if (obj == null) {
				page = this.memberGroupRepo.findAll(pageable);
				obj = new MemberGroup();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.memberGroupRepo.findAll(pageable);
				} else {
					page = this.memberGroupRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("managerSearch_memberGroup", obj);
			model.addAttribute("memberGroup", obj);
			
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
			
			request.getSession().setAttribute("listMemberGroupManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listMemberGroupManager_totalPages", totalPages);
			
			model.addAttribute("listMemberGroup", page.getContent());
			
			return "manager/memberGroup/list";
		
		} catch(Exception e) {
			return "redirect:/manager/memberGroup/list";
		}
	}
}

