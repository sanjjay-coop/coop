package org.pf.coop.portal.controller.home.title;

import java.security.Principal;
import java.util.List;

import org.pf.coop.forms.SimpleSearchForm;
import org.pf.coop.portal.controller.home.HomeBaseController;
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
public class HomeTitleSearchController extends HomeBaseController {

	@Autowired
	private TitleRepo titleRepo;
	
	@Autowired
	private SimpleSearchFormValidator validator;
	
	@GetMapping("/home/title/search")
	public String titleSearch(Model model, Principal principal, HttpServletRequest request) {
		
		SimpleSearchForm ssf = (SimpleSearchForm) request.getSession().getAttribute("searchTitleForm");
		
		if (ssf == null) ssf = new SimpleSearchForm();
		
		model.addAttribute("simpleSearchForm", ssf);
		
		return "home/title/search";
		
	}
	
	@PostMapping("/home/title/search")
	public String titleSearch(@ModelAttribute SimpleSearchForm simpleSearchForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		request.getSession().setAttribute("searchTitleForm", simpleSearchForm);
		
		this.validator.validate(simpleSearchForm, result);
		
		if (result.hasErrors()) {
			return "home/title/search";
		}
		
		List<Title> listTitle = this.titleRepo.listTitle(simpleSearchForm.getSearchString().toLowerCase());
		
		if (listTitle.isEmpty()) {
			reat.addFlashAttribute("message", "0 results found.");
			return "redirect:/home/title/search";
		}
		
		model.addAttribute("listTitle", listTitle);
		
		return "home/title/searchResult";
		
	}
	
	@GetMapping("/home/title/searchResult")
	public String titleSearch(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {

		SimpleSearchForm ssf = (SimpleSearchForm) request.getSession().getAttribute("searchTitleForm");
		
		if (ssf == null) {
			reat.addFlashAttribute("message", "No search options are avaialable.");
			return "redirect:/home/title/search";
		}
		
		List<Title> listTitle = this.titleRepo.listTitle(ssf.getSearchString().toLowerCase());
		
		if (listTitle.isEmpty()) {
			reat.addFlashAttribute("message", "0 results found.");
			return "redirect:/home/title/search";
		}
		
		model.addAttribute("listTitle", listTitle);
		
		return "home/title/searchResult";
		
	}
}
