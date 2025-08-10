package org.pf.coop.portal.controller.open;

import java.security.Principal;

import org.pf.coop.portal.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PrivacyController extends BaseController {

	@GetMapping("/open/privacyPolicy")
	public String privacyPolicyView(Model model, Principal principal) {
		
		return "open/privacy";	
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "privacy";
	}
}
