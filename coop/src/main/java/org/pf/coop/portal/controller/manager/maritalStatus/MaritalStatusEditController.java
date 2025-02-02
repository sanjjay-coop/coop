package org.pf.coop.portal.controller.manager.maritalStatus;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.pf.coop.portal.service.MaritalStatusService;
import org.pf.coop.portal.validators.edit.MaritalStatusEditValidator;
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
@RequestMapping("/manager/maritalStatus/edit")
public class MaritalStatusEditController extends ManagerBaseController {

	@Autowired
	private MaritalStatusService maritalStatusService;
	
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	
	@Autowired
	private MaritalStatusEditValidator maritalStatusEditValidator;
		
	@GetMapping("/{id}")
	public String editMaritalStatus(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			MaritalStatus maritalStatus = this.maritalStatusRepo.getReferenceById(id);
			
			if (maritalStatus == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/maritalStatus/addNew";
			}
	
			model.addAttribute("maritalStatus", maritalStatus);
			
			return "manager/maritalStatus/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/maritalStatus/addNew";
		}
	}

	@PostMapping("/*")
	public String editMaritalStatus(@ModelAttribute MaritalStatus maritalStatus,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.maritalStatusEditValidator.validate(maritalStatus, result);
		
		if (result.hasErrors()) {
			return "manager/maritalStatus/edit";
		}
		
		try {
			TransactionResult tr = this.maritalStatusService.updateMaritalStatus(maritalStatus, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/maritalStatus/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/maritalStatus/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/maritalStatus/edit/"+maritalStatus.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/maritalStatus/edit/"+maritalStatus.getId();
		}
	}
}
