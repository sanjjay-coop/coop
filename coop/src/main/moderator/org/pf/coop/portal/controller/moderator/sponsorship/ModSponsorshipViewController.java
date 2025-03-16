package org.pf.coop.portal.controller.moderator.sponsorship;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.service.SponsorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/sponsorship/view")
public class ModSponsorshipViewController extends ModeratorBaseController {

	@Autowired
	private SponsorshipService sponsorshipService;
	
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
			
			return "moderator/sponsorship/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/sponsorship/list/current";
		}
	}
}
