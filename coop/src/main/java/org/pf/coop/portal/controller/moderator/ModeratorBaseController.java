package org.pf.coop.portal.controller.moderator;

import org.pf.coop.portal.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class ModeratorBaseController extends BaseController {

	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "moderator";
	}
	
}
