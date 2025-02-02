package org.pf.coop.portal.controller.library;

import java.security.Principal;
import java.util.List;

import org.pf.coop.forms.SimpleSearchForm;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.library.TitleRepo;
import org.pf.coop.portal.validators.SimpleSearchFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LibraryController extends LibraryBaseController {
	
	@Autowired
	private TitleRepo titleRepo;
	
	@Autowired
	private SimpleSearchFormValidator validator;
	
	@GetMapping("/library")
	public String libraryView(Model model, Principal principal) {
		
		SimpleSearchForm simpleSearchForm = new SimpleSearchForm();
		
		model.addAttribute(simpleSearchForm);
		
		return "library/default";
		
	}
	
	@PostMapping("/library")
	public String libraryView(@ModelAttribute SimpleSearchForm simpleSearchForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		request.getSession().setAttribute("searchTitleForm", simpleSearchForm);
		
		this.validator.validate(simpleSearchForm, result);
		
		if (result.hasErrors()) {
			return "library/default";
		}
		
		List<Title> listTitle = this.titleRepo.listTitle(simpleSearchForm.getSearchString().toLowerCase());
		
		if (listTitle.isEmpty()) {
			reat.addFlashAttribute("message", "0 results found.");
			return "redirect:/library";
		}
		
		model.addAttribute("listTitle", listTitle);
		
		return "library/title/searchResult";
		
	}
}
