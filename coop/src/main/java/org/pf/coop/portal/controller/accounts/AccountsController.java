package org.pf.coop.portal.controller.accounts;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountsController extends AccountsBaseController {
	
	@GetMapping("/accounts")
	public String accountsView(Model model, Principal principal) {
		
		return "accounts/default";
		
	}
}
