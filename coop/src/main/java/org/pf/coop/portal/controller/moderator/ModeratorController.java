package org.pf.coop.portal.controller.moderator;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeratorController extends ModeratorBaseController {
	
	@GetMapping("/moderator")
	public String moderatorView(Model model, Principal principal) {
		
		return "moderator/default";
		
	}
}
