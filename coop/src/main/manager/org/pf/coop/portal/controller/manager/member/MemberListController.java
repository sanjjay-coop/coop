package org.pf.coop.portal.controller.manager.member;

import java.security.Principal;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
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
@RequestMapping("/manager/member")
public class MemberListController extends ManagerBaseController {
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/list")
	public String listMember(Model model, Principal principal, HttpServletRequest request) {
		
		request.getSession().setAttribute("listMember_type", "list");
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "subEndDate"));
		
		Page<Member> page = this.memberRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listMember", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listMember_pageNumber", pageNumber);
		request.getSession().setAttribute("listMember_totalPages", totalPages);
		
		return "manager/member/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMember(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		request.getSession().setAttribute("listMember_type", "list");
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listMember_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listMember_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/member/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "subEndDate"));
			
			Page<Member> page = this.memberRepo.findAll(pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listMember_pageNumber", pageNumber);
			request.getSession().setAttribute("listMember_totalPages", totalPages);
			
			model.addAttribute("listMember", page.getContent());
			
			return "manager/member/list";
		
		} catch(Exception e) {
			return "redirect:/manager/member/list";
		}
	}
}