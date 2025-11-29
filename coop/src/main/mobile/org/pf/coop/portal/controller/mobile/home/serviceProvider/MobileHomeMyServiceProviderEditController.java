package org.pf.coop.portal.controller.mobile.home.serviceProvider;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ServiceProviderRepo;
import org.pf.coop.portal.service.ServiceProviderService;
import org.pf.coop.portal.validators.ServiceProviderValidator;
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
@RequestMapping("/mobile/home/serviceProvider/edit")
public class MobileHomeMyServiceProviderEditController extends MobileBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Autowired
	private ServiceProviderValidator serviceProviderValidator;

	@GetMapping("/{id}")
	public String editServiceProvider(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			ServiceProvider serviceProvider = this.serviceProviderRepo.findByIdAndOwner(id, member);
			
			if (serviceProvider == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/mobile/home/serviceProvider/list/current";
			}
			
			model.addAttribute("serviceProvider", serviceProvider);
			
			return "mobile/home/serviceProvider/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/mobile/home/serviceProvider/list/current";
		}
	}

	@PostMapping("/*")
	public String editServiceProvider(@ModelAttribute ServiceProvider serviceProvider,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.serviceProviderValidator.validate(serviceProvider, result);
		
		if (result.hasErrors()) {
			return "mobile/home/serviceProvider/edit";
		}
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			ServiceProvider emp = this.serviceProviderRepo.findByIdAndOwner(serviceProvider.getId(), member);
			
			if (emp == null) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/mobile/home/serviceProvider/list/current";
			}
			
			serviceProvider.setOwner(emp.getOwner());
			
			TransactionResult tr = this.serviceProviderService.updateServiceProvider(serviceProvider, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not editd.");
				return "redirect:/mobile/home/serviceProvider/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record editd successfully.");
					return "redirect:/mobile/home/serviceProvider/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/mobile/home/serviceProvider/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/mobile/home/serviceProvider/list/current";
		}
	}
}
