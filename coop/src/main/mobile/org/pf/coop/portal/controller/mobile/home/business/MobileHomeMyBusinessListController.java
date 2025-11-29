package org.pf.coop.portal.controller.mobile.home.business;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.BusinessService;
import org.pf.coop.portal.validators.BusinessFileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mobile/home/business")
public class MobileHomeMyBusinessListController extends MobileBaseController {
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private BusinessFileValidator businessFileValidator;
	
	@PostMapping({"/list", "/list/*", "/list/*/*"})
	public String uploadFile(@ModelAttribute Business business,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Business obj = this.businessRepo.findByIdAndOwner(business.getId(), this.memberRepo.findByMemIdIgnoreCase(principal.getName()));
		
		if (obj==null) {
			reat.addFlashAttribute("message", "No such record is found.");
			return "redirect:/mobile/home/business/list/current";
		}
		
		this.businessFileValidator.validate(business, result);
		
		if (result.hasErrors()) {
			
			reat.addFlashAttribute("message", "Unable to upload file. File must be PNG and less than 200KB.");
			return "redirect:/mobile/home/business/list/current";
		}
		
		try {
			TransactionResult tr = this.businessService.updateBusinessPhoto(business.getFile(), business, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Business photo updation failed. Please try again later.");
				return "redirect:/mobile/home/business/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Business photo updated successfully.");
				return "redirect:/mobile/home/business/list/current";
			} else {
				reat.addFlashAttribute("message", "Business updation failed. Please try again later.");
				return "redirect:/mobile/home/business/list/current";
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/mobile/home/business/list/current";
		}
	}
	
	@GetMapping("/list")
	public String listBusiness(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "businessName"));
		
		Page<Business> page = this.businessRepo.findByOwner(member, pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listBusiness", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listBusiness_pageNumber", pageNumber);
		request.getSession().setAttribute("listBusiness_totalPages", totalPages);
		
		model.addAttribute("business", new Business());
		
		return "mobile/home/business/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listBusiness(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listBusiness_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listBusiness_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/business/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "businessName"));
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Page<Business> page = this.businessRepo.findByOwner(member, pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listBusiness_pageNumber", pageNumber);
			request.getSession().setAttribute("listBusiness_totalPages", totalPages);
			
			model.addAttribute("listBusiness", page.getContent());
			
			model.addAttribute("business", new Business());
			
			return "mobile/home/business/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/business/list";
		}
	}
}