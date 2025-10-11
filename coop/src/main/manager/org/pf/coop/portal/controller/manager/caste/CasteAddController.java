package org.pf.coop.portal.controller.manager.caste;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.service.CasteService;
import org.pf.coop.portal.validators.add.CasteAddValidator;
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
@RequestMapping(value = "/manager/caste/addNew")
public class CasteAddController extends ManagerBaseController {
	
	@Autowired
	private CasteService casteService;
	
	@Autowired
	private CasteAddValidator casteAddValidator;
	
	@GetMapping
	public String casteAdd(Model model) {
		
		Caste caste = new Caste();
		
		model.addAttribute(caste);
		
		return "manager/caste/addNew";
	}
	
	@PostMapping
	public String casteAdd(@ModelAttribute Caste caste,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.casteAddValidator.validate(caste, result);
		
		if (result.hasErrors()) {
			return "manager/caste/addNew";
		}
		
		try {
			TransactionResult tr = this.casteService.addCaste(caste, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/caste/addNew";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/caste/list/current";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/caste/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/caste/addNew";
		}
	}
}
