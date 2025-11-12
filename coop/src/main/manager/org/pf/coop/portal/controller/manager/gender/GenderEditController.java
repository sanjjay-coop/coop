package org.pf.coop.portal.controller.manager.gender;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.service.GenderService;
import org.pf.coop.portal.validators.edit.GenderEditValidator;
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
@RequestMapping("/manager/gender/edit")
public class GenderEditController extends ManagerBaseController {

	@Autowired
	private GenderService genderService;
	
	@Autowired
	private GenderEditValidator genderEditValidator;
		
	@GetMapping("/{id}")
	public String editGender(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Gender gender = (Gender) this.genderService.getById(id);
			
			if (gender == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/gender/addNew";
			}
	
			model.addAttribute("gender", gender);
			
			return "manager/gender/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/gender/addNew";
		}
	}

	@PostMapping("/*")
	public String editGender(@ModelAttribute Gender gender,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.genderEditValidator.validate(gender, result);
		
		if (result.hasErrors()) {
			return "manager/gender/edit";
		}
		
		try {
			TransactionResult tr = this.genderService.updateGender(gender, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/gender/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/gender/addNew";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/gender/addNew";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/gender/addNew";
		}
	}
}
