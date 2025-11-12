package org.pf.coop.portal.controller.manager.tribe;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.service.TribeService;
import org.pf.coop.portal.validators.add.TribeAddValidator;
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
@RequestMapping(value = "/manager/tribe/addNew")
public class TribeAddController extends ManagerBaseController {
	
	@Autowired
	private TribeService tribeService;
	
	@Autowired
	private TribeAddValidator tribeAddValidator;
	
	@GetMapping
	public String tribeAdd(Model model) {
		
		Tribe tribe = new Tribe();
		
		model.addAttribute(tribe);
		
		return "manager/tribe/addNew";
	}
	
	@PostMapping
	public String tribeAdd(@ModelAttribute Tribe tribe,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.tribeAddValidator.validate(tribe, result);
		
		if (result.hasErrors()) {
			return "manager/tribe/addNew";
		}
		
		try {
			TransactionResult tr = this.tribeService.addTribe(tribe, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/tribe/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/tribe/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/tribe/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/tribe/list/current";
		}
	}
}
