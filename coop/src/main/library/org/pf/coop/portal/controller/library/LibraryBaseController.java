package org.pf.coop.portal.controller.library;

import org.pf.coop.portal.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class LibraryBaseController extends BaseController {
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "library";
	}
	
}
