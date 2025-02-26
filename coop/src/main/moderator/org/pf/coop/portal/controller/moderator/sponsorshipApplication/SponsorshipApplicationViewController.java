package org.pf.coop.portal.controller.moderator.sponsorshipApplication;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.service.SponsorshipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/sponsorshipApplication/view")
public class SponsorshipApplicationViewController extends ModeratorBaseController {
	
	@Autowired
	private SponsorshipApplicationService sponsorshipApplicationService;
	
	@GetMapping("/{id}")
	public String editSponsorshipApplication(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			SponsorshipApplication sponsorshipApplication = (SponsorshipApplication) this.sponsorshipApplicationService.getById(id);
			
			if (sponsorshipApplication == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/sponsorshipApplication/list/current";
			}
	
			model.addAttribute("sponsorshipApplication", sponsorshipApplication);
			
			return "moderator/sponsorshipApplication/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/sponsorshipApplication/list/current";
		}
	}
}