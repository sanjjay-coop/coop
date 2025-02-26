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
import org.pf.coop.portal.validators.library.edit.TitleEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/library/title/edit")
public class TitleEditController extends LibraryBaseController {

	@Autowired
	private TitleService titleService;
	
	@Autowired
	private TitleEditValidator titleEditValidator;
	
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
		
	@GetMapping("/{id}")
	public String editTitle(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Title title = (Title) this.titleService.getById(id);
			
			if (title == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/library/title/addNew";
			}
	
			model.addAttribute("title", title);
			
			return "library/title/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/library/title/addNew";
		}
	}

	@PostMapping("/*")
	public String editTitle(@ModelAttribute Title title,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.titleEditValidator.validate(title, result);
		
		if (result.hasErrors()) {
			return "library/title/edit";
		}
		
		try {
			TransactionResult tr = this.titleService.updateTitle(title, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/library/title/view/"+title.getId();
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/library/title/view/"+title.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/library/title/edit/"+title.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/library/title/view/"+title.getId();
		}
	}
}

