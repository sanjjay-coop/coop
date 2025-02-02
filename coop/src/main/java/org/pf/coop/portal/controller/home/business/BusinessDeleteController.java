package org.pf.coop.portal.controller.home.business;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/business/delete")
public class BusinessDeleteController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private BusinessService businessService;

	@GetMapping("/{id}")
	public String deleteBusiness(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Business business = this.businessRepo.getReferenceById(id);
			
			if (business == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/business/addNew";
			}
			
			if (!business.getOwner().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/home";
			}
			
			TransactionResult tr = this.businessService.deleteBusiness(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/home/business/addNew";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/home/business/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/home/business/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/home/business/list/current";
			
		}
	}
}
