package org.pf.coop.portal.controller.mobile.home.invitation;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mobile/home/invitation")
public class MobileHomeInvitationListController extends MobileBaseController {
	
	@Autowired
	private InvitationRepo invitationRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listInvitation(@ModelAttribute Invitation invitation, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("Search_invitation_home", invitation);
				
			return "redirect:/mobile/home/invitation/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/home";
		}
	}
	
	@GetMapping("/list")
	public String listInvitation(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Invitation> page;
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Invitation obj = (Invitation) request.getSession().getAttribute("Search_invitation_home");
			
			if (obj == null) {
				page = this.invitationRepo.findByMember(member, pageable);
				obj = new Invitation();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.invitationRepo.findByMember(member, pageable);
				} else {
					page = this.invitationRepo.findByMemberAndSearchStringContainingIgnoreCase(member, obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("Search_invitation_home", obj);
			model.addAttribute("invitation", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listInvitation", page.getContent());
			
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
			
			request.getSession().setAttribute("listInvitationHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listInvitationHome_totalPages", totalPages);
			
			return "/mobile/home/invitation/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listInvitation(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listInvitationHome_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listInvitationHome_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/invitation/list";
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
			
			Page<Invitation> page;
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Invitation obj = (Invitation) request.getSession().getAttribute("Search_invitation_home");
			
			if (obj == null) {
				page = this.invitationRepo.findByMember(member, pageable);
				obj = new Invitation();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.invitationRepo.findByMember(member, pageable);
				} else {
					page = this.invitationRepo.findByMemberAndSearchStringContainingIgnoreCase(member, obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("Search_invitation_home", obj);
			model.addAttribute("invitation", obj);
			
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
			
			request.getSession().setAttribute("listInvitationHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listInvitationHome_totalPages", totalPages);
			
			model.addAttribute("listInvitation", page.getContent());
			
			return "/mobile/home/invitation/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/invitation/list";
		}
	}
}
