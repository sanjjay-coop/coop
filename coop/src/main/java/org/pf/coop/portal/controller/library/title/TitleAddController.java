package org.pf.coop.portal.controller.library.title;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.library.LibraryBaseController;
import org.pf.coop.portal.model.library.Library;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.model.library.TitleType;
import org.pf.coop.portal.repository.library.LibraryRepo;
import org.pf.coop.portal.repository.library.TitleTypeRepo;
import org.pf.coop.portal.service.library.TitleService;
import org.pf.coop.portal.validators.library.add.TitleAddValidator;
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
@RequestMapping(value = "/library/title/addNew")
public class TitleAddController extends LibraryBaseController {
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private TitleAddValidator titleAddValidator;
	
	@Autowired
	private LibraryRepo libraryRepo;
	
	@ModelAttribute("listLibrary")
	public List<Library> getListLibrary(){
		return (List<Library>) this.libraryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@Autowired
	private TitleTypeRepo titleTypeRepo;
	
	@ModelAttribute("listTitleType")
	public List<TitleType> getListTitleType(){
		return (List<TitleType>) this.titleTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String titleAdd(Model model) {
		
		Title title = new Title();
		
		model.addAttribute(title);
		
		return "library/title/addNew";
	}
	
	@PostMapping
	public String titleAdd(@ModelAttribute Title title,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.titleAddValidator.validate(title, result);
		
		if (result.hasErrors()) {
			return "library/title/addNew";
		}
		
		try {
			TransactionResult tr = this.titleService.addTitle(title, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "library/title/addNew";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
			} else {
				reat.addFlashAttribute("message", tr.getMessage());
			}
			
			return "redirect:/library/title/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "library/title/addNew";
		}
	}
}
