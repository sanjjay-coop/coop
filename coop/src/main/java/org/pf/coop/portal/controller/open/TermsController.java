package org.pf.coop.portal.controller.open;

import java.security.Principal;

import org.pf.coop.portal.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TermsController extends BaseController {

	@GetMapping("/open/termsAndConditions")
	public String termsView(Model model, Principal principal) {
		
		return "open/terms";	
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "terms";
	}
}
