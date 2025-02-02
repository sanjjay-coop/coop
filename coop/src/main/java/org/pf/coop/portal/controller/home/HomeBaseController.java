package org.pf.coop.portal.controller.home;

import org.pf.coop.portal.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class HomeBaseController extends BaseController {
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "home";
	}

}
