package org.pf.coop.portal.controller.mobile.home.business;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.BusinessService;
import org.pf.coop.portal.validators.BusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/home/business/edit")
public class MobileHomeMyBusinessEditController extends MobileBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private BusinessValidator businessValidator;

	@GetMapping("/{id}")
	public String editBusiness(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Business business = this.businessRepo.findByIdAndOwner(id, member);
			
			if (business == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/mobile/home/business/list/current";
			}
			
			model.addAttribute("business", business);
			
			return "mobile/home/business/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/business/list/current";
		}
	}

	@PostMapping("/*")
	public String editBusiness(@ModelAttribute Business business,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.businessValidator.validate(business, result);
		
		if (result.hasErrors()) {
			return "mobile/home/business/edit";
		}
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Business emp = this.businessRepo.findByIdAndOwner(business.getId(), member);
			
			if (emp == null) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/mobile/home/business/list/current";
			}
			
			business.setOwner(emp.getOwner());
			
			TransactionResult tr = this.businessService.updateBusiness(business, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not edited.");
				return "redirect:/mobile/home/business/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record edited successfully.");
					return "redirect:/mobile/home/business/list/current";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/mobile/home/business/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/mobile/home/business/list/current";
		}
	}
}
