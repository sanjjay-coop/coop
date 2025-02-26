package org.pf.coop.portal.controller.manager.contact;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.repository.ContactRepo;
import org.pf.coop.portal.service.ContactService;
import org.pf.coop.portal.validators.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/contact/edit")
public class ContactEditController extends ManagerBaseController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@Autowired
	private ContactValidator contactValidator;
		
	@GetMapping("/{id}")
	public String editContact(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Contact contact = this.contactRepo.getReferenceById(id);
			
			if (contact == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/contact/addNew";
			}
	
			model.addAttribute("contact", contact);
			
			return "manager/contact/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/contact/addNew";
		}
	}

	@PostMapping("/*")
	public String editContact(@ModelAttribute Contact contact,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.contactValidator.validate(contact, result);
		
		if (result.hasErrors()) {
			return "manager/contact/edit";
		}
		
		try {
			TransactionResult tr = this.contactService.updateContact(contact, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/contact/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/contact/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/contact/edit/"+contact.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/contact/edit/"+contact.getId();
		}
	}
}
