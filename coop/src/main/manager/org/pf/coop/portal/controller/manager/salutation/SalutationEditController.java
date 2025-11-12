package org.pf.coop.portal.controller.manager.salutation;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.service.SalutationService;
import org.pf.coop.portal.validators.edit.SalutationEditValidator;
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
@RequestMapping("/manager/salutation/edit")
public class SalutationEditController extends ManagerBaseController {

	@Autowired
	private SalutationService salutationService;
	
	@Autowired
	private SalutationEditValidator salutationEditValidator;
		
	@GetMapping("/{id}")
	public String editSalutation(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Salutation salutation = (Salutation) this.salutationService.getById(id);
			
			if (salutation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/salutation/addNew";
			}
	
			model.addAttribute("salutation", salutation);
			
			return "manager/salutation/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/salutation/addNew";
		}
	}

	@PostMapping("/*")
	public String editSalutation(@ModelAttribute Salutation salutation,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.salutationEditValidator.validate(salutation, result);
		
		if (result.hasErrors()) {
			return "manager/salutation/edit";
		}
		
		try {
			TransactionResult tr = this.salutationService.updateSalutation(salutation, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/salutation/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/salutation/addNew";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/salutation/addNew";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/salutation/addNew";
		}
	}
}
