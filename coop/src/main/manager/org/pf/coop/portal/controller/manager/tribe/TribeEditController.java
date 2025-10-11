package org.pf.coop.portal.controller.manager.tribe;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.service.TribeService;
import org.pf.coop.portal.validators.edit.TribeEditValidator;
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
@RequestMapping("/manager/tribe/edit")
public class TribeEditController extends ManagerBaseController {

	@Autowired
	private TribeService tribeService;
	
	@Autowired
	private TribeEditValidator tribeEditValidator;
		
	@GetMapping("/{id}")
	public String editTribe(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Tribe tribe = (Tribe) this.tribeService.getById(id);
			
			if (tribe == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/tribe/list/current";
			}
	
			model.addAttribute("tribe", tribe);
			
			return "manager/tribe/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/tribe/list/current";
		}
	}

	@PostMapping("/*")
	public String editTribe(@ModelAttribute Tribe tribe,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.tribeEditValidator.validate(tribe, result);
		
		if (result.hasErrors()) {
			return "manager/tribe/edit";
		}
		
		try {
			TransactionResult tr = this.tribeService.updateTribe(tribe, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/tribe/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/tribe/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/tribe/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/tribe/list/current";
		}
	}
}
