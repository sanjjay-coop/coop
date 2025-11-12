package org.pf.coop.portal.controller.moderator.document;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Document;
import org.pf.coop.portal.service.DocumentService;
import org.pf.coop.portal.validators.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/moderator/document/addNew")
public class ModDocumentAddController extends ModeratorBaseController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentValidator documentValidator;
	
	@GetMapping
	public String documentAdd(Model model) {
		
		Document document = new Document();
		
		model.addAttribute(document);
		
		return "moderator/document/addNew";
	}
	
	@PostMapping
	public String documentAdd(@ModelAttribute Document document,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.documentValidator.validate(document, result);
		
		if (result.hasErrors()) {
			return "moderator/document/addNew";
		}
		
		try {
			TransactionResult tr = this.documentService.addDocument(document, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/moderator/document/list/current";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/moderator/document/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/document/list/current";
			}
			
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/document/list/current";
		}
	}
}