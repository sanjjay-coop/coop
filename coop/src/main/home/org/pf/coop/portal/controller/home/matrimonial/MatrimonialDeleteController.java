package org.pf.coop.portal.controller.home.matrimonial;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MatrimonialRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.MatrimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/matrimonial/delete")
public class MatrimonialDeleteController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MatrimonialRepo matrimonialRepo;
	
	@Autowired
	private MatrimonialService matrimonialService;

	@GetMapping("/{id}")
	public String deleteMatrimonial(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Matrimonial matrimonial = this.matrimonialRepo.getReferenceById(id);
			
			if (matrimonial == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/matrimonial/addNew";
			}
			
			if (!matrimonial.getOwner().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/home";
			}
			
			TransactionResult tr = this.matrimonialService.deleteMatrimonial(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/home/matrimonial/addNew";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/home/matrimonial/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/home/matrimonial/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/home/matrimonial/list/current";
			
		}
	}
}