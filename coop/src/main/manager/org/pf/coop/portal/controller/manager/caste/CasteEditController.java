package org.pf.coop.portal.controller.manager.caste;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.service.CasteService;
import org.pf.coop.portal.validators.edit.CasteEditValidator;
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
@RequestMapping("/manager/caste/edit")
public class CasteEditController extends ManagerBaseController {

	@Autowired
	private CasteService casteService;
	
	@Autowired
	private CasteEditValidator casteEditValidator;
		
	@GetMapping("/{id}")
	public String editCaste(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Caste caste = (Caste) this.casteService.getById(id);
			
			if (caste == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/caste/list/current";
			}
	
			model.addAttribute("caste", caste);
			
			return "manager/caste/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/caste/list/current";
		}
	}

	@PostMapping("/*")
	public String editCaste(@ModelAttribute Caste caste,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.casteEditValidator.validate(caste, result);
		
		if (result.hasErrors()) {
			return "manager/caste/edit";
		}
		
		try {
			TransactionResult tr = this.casteService.updateCaste(caste, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/caste/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/caste/list/current";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/caste/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/caste/list/current";
		}
	}
}

