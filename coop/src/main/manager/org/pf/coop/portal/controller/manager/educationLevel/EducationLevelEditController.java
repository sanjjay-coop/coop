package org.pf.coop.portal.controller.manager.educationLevel;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.service.EducationLevelService;
import org.pf.coop.portal.validators.edit.EducationLevelEditValidator;
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
@RequestMapping("/manager/educationLevel/edit")
public class EducationLevelEditController extends ManagerBaseController {

	@Autowired
	private EducationLevelService educationLevelService;
	
	@Autowired
	private EducationLevelEditValidator educationLevelEditValidator;
		
	@GetMapping("/{id}")
	public String editEducationLevel(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			EducationLevel educationLevel = (EducationLevel) this.educationLevelService.getById(id);
			
			if (educationLevel == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/educationLevel/addNew";
			}
	
			model.addAttribute("educationLevel", educationLevel);
			
			return "manager/educationLevel/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/educationLevel/addNew";
		}
	}

	@PostMapping("/*")
	public String editEducationLevel(@ModelAttribute EducationLevel educationLevel,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.educationLevelEditValidator.validate(educationLevel, result);
		
		if (result.hasErrors()) {
			return "manager/educationLevel/edit";
		}
		
		try {
			TransactionResult tr = this.educationLevelService.updateEducationLevel(educationLevel, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/educationLevel/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/educationLevel/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/educationLevel/addNew";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/educationLevel/addNew";
		}
	}
}
