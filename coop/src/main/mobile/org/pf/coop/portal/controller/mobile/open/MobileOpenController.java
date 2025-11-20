package org.pf.coop.portal.controller.mobile.open;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mobile/open")
public class MobileOpenController extends MobileBaseController {

	@GetMapping("/privacyPolicy")
	public String privacyPolicyView(Model model, Principal principal) {
		
		return "mobile/open/privacyPolicy";
		
	}

	@GetMapping("/termsAndConditions")
	public String termsAndConditionsView(Model model, Principal principal) {
		
		return "mobile/open/termsAndConditions";
		
	}

	@GetMapping("/disclaimer")
	public String disclaimerView(Model model, Principal principal) {
		
		return "mobile/open/disclaimer";
		
	}
}
