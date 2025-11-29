package org.pf.coop.portal.controller.mobile.home.funding;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.FundingService;
import org.pf.coop.portal.validators.FundingValidator;
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
@RequestMapping(value = "/mobile/home/funding/addNew")
public class MobileHomeFundingAddController extends MobileBaseController {

	@Autowired
	private MemberRepo memberRepo;

	@Autowired
	private FundingService fundingService;
	
	@Autowired
	private FundingValidator fundingValidator;
	
	@GetMapping
	public String fundingAdd(Model model) {
		
		Funding funding = new Funding();
		
		model.addAttribute(funding);
		
		return "mobile/home/funding/addNew";
	}
	
	@PostMapping
	public String fundingAdd(@ModelAttribute Funding funding,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		funding.setApplicant(member);
		
		this.fundingValidator.validate(funding, result);
		
		if (result.hasErrors()) {
			return "mobile/home/funding/addNew";
		}
		
		try {
			TransactionResult tr = this.fundingService.addFunding(funding, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/mobile/home/funding/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/mobile/home/funding/list";				
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/mobile/home/funding/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/mobile/home/funding/current";
		}
	}
}

