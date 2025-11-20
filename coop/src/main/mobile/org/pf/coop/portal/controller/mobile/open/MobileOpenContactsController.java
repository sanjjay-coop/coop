package org.pf.coop.portal.controller.mobile.open;

import java.security.Principal;
import java.util.List;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mobile/open/contacts")
public class MobileOpenContactsController extends MobileBaseController {

	@Autowired
	private ContactRepo contactRepo;
	
	@GetMapping
	public String errorView(Model model, Principal principal) {
		

		List<Contact> listContact = this.contactRepo.findAll(Sort.by(Sort.Direction.DESC, "name"));
		
		model.addAttribute("listContact", listContact);

		return "mobile/open/contacts";
		
	}
}
