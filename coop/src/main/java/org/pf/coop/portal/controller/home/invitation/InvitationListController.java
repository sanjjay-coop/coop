package org.pf.coop.portal.controller.home.invitation;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.InvitationRepo;
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
@RequestMapping("/home/invitation")
public class InvitationListController extends HomeBaseController {
	
	@Autowired
	private InvitationRepo invitationRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/list")
	public String listInvitation(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "date"));
		
		Page<Invitation> page = this.invitationRepo.findByMember(member, pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listInvitation", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listInvitation_pageNumber", pageNumber);
		request.getSession().setAttribute("listInvitation_totalPages", totalPages);
		
		return "home/invitation/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listInvitation(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listInvitation_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listInvitation_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/invitation/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "date"));
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Page<Invitation> page = this.invitationRepo.findByMember(member, pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listInvitation_pageNumber", pageNumber);
			request.getSession().setAttribute("listInvitation_totalPages", totalPages);
			
			model.addAttribute("listInvitation", page.getContent());
			
			return "home/invitation/list";
		
		} catch(Exception e) {
			return "redirect:/home/invitation/list";
		}
	}
}