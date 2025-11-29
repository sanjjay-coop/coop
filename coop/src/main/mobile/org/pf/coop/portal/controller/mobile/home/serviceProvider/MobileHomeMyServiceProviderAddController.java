package org.pf.coop.portal.controller.mobile.home.serviceProvider;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.ServiceProviderService;
import org.pf.coop.portal.validators.ServiceProviderValidator;
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
@RequestMapping(value = "/mobile/home/serviceProvider/addNew")
public class MobileHomeMyServiceProviderAddController extends MobileBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@Autowired
	private ServiceProviderValidator serviceProviderValidator;
	
	@GetMapping
	public String serviceProviderAdd(Model model) {
		
		ServiceProvider serviceProvider = new ServiceProvider();
		
		model.addAttribute(serviceProvider);
		
		return "mobile/home/serviceProvider/addNew";
	}
	
	@PostMapping
	public String serviceProviderAdd(@ModelAttribute ServiceProvider serviceProvider,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		serviceProvider.setOwner(member);
		
		this.serviceProviderValidator.validate(serviceProvider, result);
		
		if (result.hasErrors()) {
			return "mobile/home/serviceProvider/addNew";
		}
		
		try {
			TransactionResult tr = this.serviceProviderService.addServiceProvider(serviceProvider, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/mobile/home/serviceProvider/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/mobile/home/serviceProvider/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/mobile/home/serviceProvider/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/mobile/home/serviceProvider/list/current";
		}
	}
}
