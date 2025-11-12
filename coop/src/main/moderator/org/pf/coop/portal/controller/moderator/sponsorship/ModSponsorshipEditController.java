package org.pf.coop.portal.controller.moderator.sponsorship;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.service.SponsorshipService;
import org.pf.coop.portal.validators.SponsorshipValidator;
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
@RequestMapping("/moderator/sponsorship/edit")
public class ModSponsorshipEditController extends ModeratorBaseController {

	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private SponsorshipValidator sponsorshipValidator;
	
	@GetMapping("/{id}")
	public String editSponsorship(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Sponsorship sponsorship = (Sponsorship) this.sponsorshipService.getById(id);
			
			if (sponsorship == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/sponsorship/list/current";
			}
	
			model.addAttribute("sponsorship", sponsorship);
			
			return "moderator/sponsorship/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/sponsorship/list/current";
		}
	}

	@PostMapping("/*")
	public String editSponsorship(@ModelAttribute Sponsorship sponsorship,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.sponsorshipValidator.validate(sponsorship, result);
		
		if (result.hasErrors()) {
			return "moderator/sponsorship/edit";
		}
		
		try {
			TransactionResult tr = this.sponsorshipService.updateSponsorship(sponsorship, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/sponsorship/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/sponsorship/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/moderator/sponsorship/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/sponsorship/list/current";
		}
	}
}
