package org.pf.coop.portal.controller.manager.module;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Module;
import org.pf.coop.portal.service.ModuleService;
import org.pf.coop.portal.validators.edit.ModuleEditValidator;
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
@RequestMapping("/manager/module/update")
public class ModuleEditController extends ManagerBaseController {

	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ModuleEditValidator moduleEditValidator;
		
	@GetMapping("/{id}")
	public String editModule(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Module module = (Module) this.moduleService.getById(id);
			
			if (module == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/module/list/current";
			}
	
			model.addAttribute("module", module);
			
			return "manager/module/update";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/module/list/current";
		}
	}

	@PostMapping("/*")
	public String editModule(@ModelAttribute Module module,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.moduleEditValidator.validate(module, result);
		
		if (result.hasErrors()) {
			return "manager/module/update";
		}
		
		try {
			TransactionResult tr = this.moduleService.updateModule(module, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/module/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/module/list/current";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/module/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/module/list/current";
		}
	}
}
