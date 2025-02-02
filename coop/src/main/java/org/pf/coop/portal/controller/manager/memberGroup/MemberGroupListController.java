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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager/memberGroup")
public class MemberGroupListController extends ManagerBaseController {
	
	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@GetMapping("/list")
	public String listMemberGroup(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
		
		Page<MemberGroup> page = this.memberGroupRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listMemberGroup", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listMemberGroup_pageNumber", pageNumber);
		request.getSession().setAttribute("listMemberGroup_totalPages", totalPages);
		
		return "manager/memberGroup/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMemberGroup(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listMemberGroup_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listMemberGroup_totalPages");
			
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
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
			
			Page<MemberGroup> page = this.memberGroupRepo.findAll(pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listMemberGroup_pageNumber", pageNumber);
			request.getSession().setAttribute("listMemberGroup_totalPages", totalPages);
			
			model.addAttribute("listMemberGroup", page.getContent());
			
			return "manager/memberGroup/list";
		
		} catch(Exception e) {
			return "redirect:/manager/memberGroup/list";
		}
	}
}

