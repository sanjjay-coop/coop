package org.pf.coop.portal.controller.manager;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController extends ManagerBaseController {


	@GetMapping("/manager")
	public String indexView(Model model, Principal principal) {
		
		return "manager/default";
		
	}
}
