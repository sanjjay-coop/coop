package org.pf.coop.portal.controller.mobile.home.serviceProvider;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ServiceProviderRepo;
import org.pf.coop.portal.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/home/serviceProvider/delete")
public class MobileHomeMyServiceProviderDeleteController extends MobileBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Autowired
	private ServiceProviderService serviceProviderService;

	@GetMapping("/{id}")
	public String deleteServiceProvider(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			ServiceProvider serviceProvider = this.serviceProviderRepo.findByIdAndOwner(id, member);
			
			if (serviceProvider == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/mobile/home/serviceProvider/list/current";
			}
			
			TransactionResult tr = this.serviceProviderService.deleteServiceProvider(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/mobile/home/serviceProvider/list/current";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/mobile/home/serviceProvider/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/mobile/home/serviceProvider/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/mobile/home/serviceProvider/list/current";
			
		}
	}
}
