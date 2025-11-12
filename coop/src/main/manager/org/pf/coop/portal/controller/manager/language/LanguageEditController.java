package org.pf.coop.portal.controller.manager.language;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Language;
import org.pf.coop.portal.repository.LanguageRepo;
import org.pf.coop.portal.service.LanguageService;
import org.pf.coop.portal.validators.edit.LanguageEditValidator;
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
@RequestMapping("/manager/language/edit")
public class LanguageEditController extends ManagerBaseController {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LanguageRepo languageRepo;
	
	@Autowired
	private LanguageEditValidator languageEditValidator;
		
	@GetMapping("/{id}")
	public String editLanguage(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Language language = this.languageRepo.getReferenceById(id);
			
			if (language == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/language/addNew";
			}
	
			model.addAttribute("language", language);
			
			return "manager/language/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/language/addNew";
		}
	}

	@PostMapping("/*")
	public String editLanguage(@ModelAttribute Language language,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.languageEditValidator.validate(language, result);
		
		if (result.hasErrors()) {
			return "manager/language/edit";
		}
		
		try {
			TransactionResult tr = this.languageService.updateLanguage(language, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/language/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/language/addNew";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/language/addNew";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/language/addNew";
		}
	}
}
