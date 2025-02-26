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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/moderator/sponsorship/addNew")
public class SponsorshipAddController extends ModeratorBaseController {

	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private SponsorshipValidator sponsorshipValidator;
	
	@GetMapping
	public String sponsorshipAdd(Model model) {
		
		Sponsorship sponsorship = new Sponsorship();
		
		model.addAttribute(sponsorship);
		
		return "moderator/sponsorship/addNew";
	}
	
	@PostMapping
	public String sponsorshipAdd(@ModelAttribute Sponsorship sponsorship,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.sponsorshipValidator.validate(sponsorship, result);
		
		if (result.hasErrors()) {
			return "moderator/sponsorship/addNew";
		}
		
		try {
			TransactionResult tr = this.sponsorshipService.addSponsorship(sponsorship, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "moderator/sponsorship/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/moderator/sponsorship/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "moderator/sponsorship/addNew";
		}
	}
}
