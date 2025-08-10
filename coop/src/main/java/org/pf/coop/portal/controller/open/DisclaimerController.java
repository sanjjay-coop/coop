package org.pf.coop.portal.controller.open;

import java.security.Principal;

import org.pf.coop.portal.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DisclaimerController extends BaseController {

	@GetMapping("/open/disclaimer")
	public String disclaimerView(Model model, Principal principal) {
		
		return "open/disclaimer";	
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "disclaimer";
	}
}
