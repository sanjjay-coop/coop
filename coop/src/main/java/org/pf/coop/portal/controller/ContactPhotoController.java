package org.pf.coop.portal.controller;

import java.security.Principal;

import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactPhotoController {

	@Autowired
	private ContactService contactService;

	@GetMapping("/open/contactPhoto/{id}")
	public ResponseEntity<byte[]> getContactPhoto(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
	    
		Contact contact = (Contact) this.contactService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + contact.getFileName() + "\"")
	        .body(contact.getFileData());
	} 

}
