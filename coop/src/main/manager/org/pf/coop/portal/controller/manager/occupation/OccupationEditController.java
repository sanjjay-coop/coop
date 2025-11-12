package org.pf.coop.portal.controller.manager.occupation;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.service.OccupationService;
import org.pf.coop.portal.validators.edit.OccupationEditValidator;
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
@RequestMapping("/manager/occupation/edit")
public class OccupationEditController extends ManagerBaseController {

	@Autowired
	private OccupationService occupationService;
	
	@Autowired
	private OccupationRepo occupationRepo;
	
	@Autowired
	private OccupationEditValidator occupationEditValidator;
		
	@GetMapping("/{id}")
	public String editOccupation(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Occupation occupation = this.occupationRepo.getReferenceById(id);
			
			if (occupation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/occupation/addNew";
			}
	
			model.addAttribute("occupation", occupation);
			
			return "manager/occupation/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/occupation/addNew";
		}
	}

	@PostMapping("/*")
	public String editOccupation(@ModelAttribute Occupation occupation,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.occupationEditValidator.validate(occupation, result);
		
		if (result.hasErrors()) {
			return "manager/occupation/edit";
		}
		
		try {
			TransactionResult tr = this.occupationService.updateOccupation(occupation, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/occupation/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/occupation/addNew";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/occupation/edit/"+occupation.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/occupation/edit/"+occupation.getId();
		}
	}
}
