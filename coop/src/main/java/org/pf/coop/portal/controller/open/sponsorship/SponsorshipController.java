package org.pf.coop.portal.controller.open.sponsorship;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.repository.SponsorshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SponsorshipController extends BaseController {

	@Autowired
	private SponsorshipRepo repo;
	
	@GetMapping("/open/sponsorship")
	public String sponsorshipView(Model model, Principal principal) {
		
		List<Sponsorship> listSponsorship = this.repo.listSponsorshipForPublication(Calendar.getInstance().getTime());
		
		model.addAttribute("listSponsorship", listSponsorship);
		
		return "open/sponsorship";	
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "sponsorship";
	}
}
