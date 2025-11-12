package org.pf.coop.portal.controller.moderator.funding;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModFundingViewController extends ModeratorBaseController {

	@Autowired
	private FundingService fundingService;
	
	@GetMapping("/moderator/funding/view/{id}")
	public String fundingView(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		try {
			Funding funding = (Funding) this.fundingService.getById(id);
			
			if (funding == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/funding/list/current";
			}
	
			model.addAttribute("funding", funding);
			
			return "moderator/funding/view";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/funding/list/current";
		}
	}
}
