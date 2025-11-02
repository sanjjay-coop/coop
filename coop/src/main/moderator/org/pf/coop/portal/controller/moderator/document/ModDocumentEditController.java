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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/document/edit")
public class ModDocumentEditController extends ModeratorBaseController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentValidator documentValidator;
	
	@GetMapping("/{id}")
	public String editDocument(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Document document = (Document) this.documentService.getById(id);
			
			if (document == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/document/addNew";
			}
	
			model.addAttribute("document", document);
			
			return "moderator/document/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/document/addNew";
		}
	}

	@PostMapping("/*")
	public String editDocument(@ModelAttribute Document document,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.documentValidator.validate(document, result);
		
		if (result.hasErrors()) {
			return "moderator/document/edit";
		}
		
		try {
			TransactionResult tr = this.documentService.updateDocument(document, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/document/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/document/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/moderator/document/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/document/list/current";
		}
	}
}
