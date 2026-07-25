package org.pf.coop.portal.controller.manager.module;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Module;
import org.pf.coop.portal.service.ModuleService;
import org.pf.coop.portal.validators.add.ModuleAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/module/addNew")
public class ModuleAddController extends ManagerBaseController {

	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ModuleAddValidator moduleAddValidator;
	
	@GetMapping
	public String moduleAdd(Model model) {
		
		Module module = new Module();
		
		model.addAttribute(module);
		
		return "manager/module/addNew";
	}
	
	@PostMapping
	public String moduleAdd(@ModelAttribute Module module,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.moduleAddValidator.validate(module, result);
		
		if (result.hasErrors()) {
			return "manager/module/addNew";
		}
		
		try {
			TransactionResult tr = this.moduleService.addModule(module, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/module/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/module/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/module/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/module/list/current";
		}
	}
}
