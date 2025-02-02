package org.pf.coop.portal.controller.manager.language;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Language;
import org.pf.coop.portal.repository.LanguageRepo;
import org.pf.coop.portal.service.LanguageService;
import org.pf.coop.portal.validators.add.LanguageAddValidator;
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
@RequestMapping(value = "/manager/language/addNew")
public class LanguageAddController extends ManagerBaseController {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LanguageAddValidator languageAddValidator;
	
	@Autowired
	private LanguageRepo languageRepo;
	
	@ModelAttribute("listLanguage")
	public List<Language> getListLanguage(){
		return (List<Language>) this.languageRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String languageAdd(Model model) {
		
		Language language = new Language();
		
		model.addAttribute(language);
		
		return "manager/language/addNew";
	}
	
	@PostMapping
	public String languageAdd(@ModelAttribute Language language,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.languageAddValidator.validate(language, result);
		
		if (result.hasErrors()) {
			return "manager/language/addNew";
		}
		
		try {
			TransactionResult tr = this.languageService.addLanguage(language, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/language/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/language/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/language/addNew";
		}
	}
}
