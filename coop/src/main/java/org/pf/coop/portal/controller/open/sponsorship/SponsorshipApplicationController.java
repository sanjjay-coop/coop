package org.pf.coop.portal.controller.open.sponsorship;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.service.SponsorshipApplicationService;
import org.pf.coop.portal.service.SponsorshipService;
import org.pf.coop.portal.validators.SponsorshipApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SponsorshipApplicationController extends BaseController {
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private SponsorshipApplicationService saService;
	
	@Autowired
	private SponsorshipApplicationValidator validator;
	
	@GetMapping("/open/sponsorship/apply/{id}")
	public String sponsorshipApplication(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			Sponsorship sponsorship = (Sponsorship) this.sponsorshipService.getActiveById(id);
			
			if (sponsorship == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/open/sponsorship";
			}
			
			SponsorshipApplication sponsorshipApplication = new SponsorshipApplication();
			
			sponsorshipApplication.setSponsorship(sponsorship);
	
			model.addAttribute("sponsorshipApplication", sponsorshipApplication);
			
			return "open/sponsorshipApplication";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/open/sponsorship";
		}
	}

	@PostMapping("/open/sponsorship/apply/*")
	public String sponsorshipApplication(@ModelAttribute SponsorshipApplication sponsorshipApplication,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.validator.validate(sponsorshipApplication, result);
		
		if (result.hasErrors()) {
			return "open/sponsorshipApplication";
		}
		
		try {
			TransactionResult tr = this.saService.addSponsorshipApplication(sponsorshipApplication, "");
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/open/sponsorship";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Application No. " + ((SponsorshipApplication) tr.getObj()).getId() + " received successfully.");
					return "redirect:/open/sponsorship";
				} else {
					reat.addFlashAttribute("message", "Record not added. Please try again later.");
					return "redirect:/open/sponsorship";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/open/sponsorship";
		}
	}
}
