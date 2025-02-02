package org.pf.coop.portal.controller.library.titleType;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.library.LibraryBaseController;
import org.pf.coop.portal.model.library.TitleType;
import org.pf.coop.portal.service.library.TitleTypeService;
import org.pf.coop.portal.validators.library.edit.TitleTypeEditValidator;
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
@RequestMapping("/library/titleType/edit")
public class TitleTypeEditController extends LibraryBaseController {

	@Autowired
	private TitleTypeService titleTypeService;
	
	@Autowired
	private TitleTypeEditValidator titleTypeEditValidator;
		
	@GetMapping("/{id}")
	public String editTitleType(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			TitleType titleType = (TitleType) this.titleTypeService.getById(id);
			
			if (titleType == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/library/titleType/addNew";
			}
	
			model.addAttribute("titleType", titleType);
			
			return "library/titleType/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/library/titleType/addNew";
		}
	}

	@PostMapping("/*")
	public String editTitleType(@ModelAttribute TitleType titleType,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.titleTypeEditValidator.validate(titleType, result);
		
		if (result.hasErrors()) {
			return "library/titleType/edit";
		}
		
		try {
			TransactionResult tr = this.titleTypeService.updateTitleType(titleType, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/library/titleType/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/library/titleType/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/library/titleType/edit/"+titleType.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/library/titleType/edit/"+titleType.getId();
		}
	}
}
