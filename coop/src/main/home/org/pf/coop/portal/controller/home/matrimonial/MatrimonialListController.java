package org.pf.coop.portal.controller.home.matrimonial;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MatrimonialRepo;
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
@RequestMapping("/home/matrimonial")
public class MatrimonialListController extends HomeBaseController {
	
	@Autowired
	private MatrimonialRepo matrimonialRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/list")
	public String listMatrimonial(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		//if (request.getSession().getAttribute("listMatrimonial_pageNumber")==null) pageNumber = 0;
		//else pageNumber = (int) request.getSession().getAttribute("listMatrimonial_pageNumber");
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "addDate"));
		
		Page<Matrimonial> page = this.matrimonialRepo.findByOwner(member, pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listMatrimonial", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listMatrimonial_pageNumber", pageNumber);
		request.getSession().setAttribute("listMatrimonial_totalPages", totalPages);
		
		return "home/matrimonial/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMatrimonial(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listMatrimonial_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listMatrimonial_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/matrimonial/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "addDate"));
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Page<Matrimonial> page = this.matrimonialRepo.findByOwner(member, pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listMatrimonial_pageNumber", pageNumber);
			request.getSession().setAttribute("listMatrimonial_totalPages", totalPages);
			
			model.addAttribute("listMatrimonial", page.getContent());
			
			return "home/matrimonial/list";
		
		} catch(Exception e) {
			return "redirect:/home/matrimonial/list";
		}
	}
}
