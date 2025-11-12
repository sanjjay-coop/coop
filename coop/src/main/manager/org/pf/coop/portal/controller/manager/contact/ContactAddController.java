package org.pf.coop.portal.controller.manager.contact;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.repository.ContactRepo;
import org.pf.coop.portal.service.ContactService;
import org.pf.coop.portal.validators.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/contact/addNew")
public class ContactAddController extends ManagerBaseController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@Autowired
	private ContactValidator contactValidator;
	
	@ModelAttribute("listContact")
	public List<Contact> getListContact(){
		return (List<Contact>) this.contactRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String contactAdd(Model model) {
		
		Contact contact = new Contact();
		
		model.addAttribute(contact);
		
		return "manager/contact/addNew";
	}
	
	@PostMapping
	public String contactAdd(@ModelAttribute Contact contact,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.contactValidator.validate(contact, result);
		
		if (result.hasErrors()) {
			return "manager/contact/addNew";
		}
		
		try {
			TransactionResult tr = this.contactService.addContact(contact, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/contact/addNew";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/contact/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/contact/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/contact/addNew";
		}
	}
}
