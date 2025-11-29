package org.pf.coop.portal.controller.mobile.home.business;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.BusinessService;
import org.pf.coop.portal.validators.BusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/mobile/home/business/addNew")
public class MobileHomeMyBusinessAddController extends MobileBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private BusinessValidator businessValidator;
	
	@GetMapping
	public String businessAdd(Model model) {
		
		Business business = new Business();
		
		model.addAttribute(business);
		
		return "mobile/home/business/addNew";
	}
	
	@PostMapping
	public String businessAdd(@ModelAttribute Business business,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		business.setOwner(member);
		
		this.businessValidator.validate(business, result);
		
		if (result.hasErrors()) {
			return "mobile/home/business/addNew";
		}
		
		try {
			TransactionResult tr = this.businessService.addBusiness(business, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/mobile/home/business/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/mobile/home/business/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/mobile/home/business/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/mobile/home/business/list/current";
		}
	}
}
