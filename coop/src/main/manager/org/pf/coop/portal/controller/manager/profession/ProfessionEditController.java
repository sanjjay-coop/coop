package org.pf.coop.portal.controller.manager.profession;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Profession;
import org.pf.coop.portal.repository.ProfessionRepo;
import org.pf.coop.portal.service.ProfessionService;
import org.pf.coop.portal.validators.edit.ProfessionEditValidator;
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
@RequestMapping("/manager/profession/edit")
public class ProfessionEditController extends ManagerBaseController {

	@Autowired
	private ProfessionService professionService;
	
	@Autowired
	private ProfessionRepo professionRepo;
	
	@Autowired
	private ProfessionEditValidator professionEditValidator;
		
	@GetMapping("/{id}")
	public String editProfession(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Profession profession = this.professionRepo.getReferenceById(id);
			
			if (profession == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/profession/addNew";
			}
	
			model.addAttribute("profession", profession);
			
			return "manager/profession/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/profession/addNew";
		}
	}

	@PostMapping("/*")
	public String editProfession(@ModelAttribute Profession profession,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.professionEditValidator.validate(profession, result);
		
		if (result.hasErrors()) {
			return "manager/profession/edit";
		}
		
		try {
			TransactionResult tr = this.professionService.updateProfession(profession, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/profession/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/profession/addNew";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/profession/addNew";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/profession/addNew";
		}
	}
}
