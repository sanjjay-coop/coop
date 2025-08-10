package org.pf.coop.portal.controller.contacts;

import java.util.List;

import org.pf.coop.portal.controller.IndexBaseController;
import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/contacts")
public class ContactsViewController extends IndexBaseController {

	@Autowired
	private ContactRepo contactRepo;
	
	@ModelAttribute("listContact")
	public List<Contact> getListContact(){
		return (List<Contact>) this.contactRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String contactsView(Model model) {
		
		Contact contact = new Contact();
		
		model.addAttribute(contact);
		
		return "contact/view";
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "contacts";
	}
}
