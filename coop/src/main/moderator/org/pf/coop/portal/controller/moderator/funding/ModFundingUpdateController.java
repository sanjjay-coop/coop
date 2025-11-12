package org.pf.coop.portal.controller.moderator.funding;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.service.FundingService;
import org.pf.coop.portal.validators.FundingUpdateStatusValidator;
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
@RequestMapping("/moderator/funding/updateStatus")
public class ModFundingUpdateController extends ModeratorBaseController {

	@Autowired
	private FundingService fundingService;
	
	@Autowired
	private FundingUpdateStatusValidator fundingValidator;
	
	@GetMapping("/{id}")
	public String updateStatus(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Funding funding = (Funding) this.fundingService.getById(id);
			
			if (funding == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/funding/list/current";
			}
			
			if (!funding.getStatus().equals("RECEIVED")) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/funding/view/"+funding.getId();
			}
	
			model.addAttribute("funding", funding);
			
			return "moderator/funding/updateStatus";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/funding/list/current";
		}
	}

	@PostMapping("/*")
	public String updateStatus(@ModelAttribute Funding funding,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.fundingValidator.validate(funding, result);
		
		if (result.hasErrors()) {
			model.addAttribute("funding", funding);
			return "moderator/funding/updateStatus";
		}
		
		try {
			TransactionResult tr = this.fundingService.updateStatus(funding, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/funding/view/"+funding.getId();
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/funding/view/"+funding.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/moderator/funding/view/"+funding.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/funding/view/"+funding.getId();
		}
		
	}
}
