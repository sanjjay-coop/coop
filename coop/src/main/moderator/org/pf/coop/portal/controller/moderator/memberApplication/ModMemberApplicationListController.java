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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/moderator/memberApplication")
public class ModMemberApplicationListController extends ModeratorBaseController {
	
	@Autowired
	private MemberApplicationRepo memberApplicationRepo;
	
	@GetMapping("/list")
	public String listMemberApplication(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		//if (request.getSession().getAttribute("listModMemberApplication_pageNumber")==null) pageNumber = 0;
		//else pageNumber = (int) request.getSession().getAttribute("listModMemberApplication_pageNumber");
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<MemberApplication> page = this.memberApplicationRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listMemberApplication", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listModMemberApplication_pageNumber", pageNumber);
		request.getSession().setAttribute("listModMemberApplication_totalPages", totalPages);
		
		return "moderator/memberApplication/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMemberApplication(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listModMemberApplication_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listModMemberApplication_totalPages");
			
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
			
			Page<MemberApplication> page = this.memberApplicationRepo.findAll(pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listModMemberApplication_pageNumber", pageNumber);
			request.getSession().setAttribute("listModMemberApplication_totalPages", totalPages);
			
			model.addAttribute("listMemberApplication", page.getContent());
			
			return "moderator/memberApplication/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/memberApplication/list";
		}
	}
}
