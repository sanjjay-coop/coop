package org.pf.coop.portal.controller.manager.parameters;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.service.ParametersService;
import org.pf.coop.portal.validators.ParametersValidator;
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
@RequestMapping(value = "/manager/parameters")
public class ParametersController extends ManagerBaseController {

	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private ParametersValidator parametersValidator;
	
	@GetMapping
	public String parameters(Model model) {
		
		Parameters parameters = this.parametersService.getParameters();
		
		if (parameters == null) parameters = new Parameters();
		
		model.addAttribute(parameters);
		
		return "manager/parameters/parameters";
	}
	
	@PostMapping
	public String parameters(@ModelAttribute Parameters parameters,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.parametersValidator.validate(parameters, result);
		
		if (result.hasErrors()) {
			return "manager/parameters/parameters";
		}
		
		try {
			TransactionResult tr = this.parametersService.addParameters(parameters, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/parameters/parameters";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/parameters";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/parameters/parameters";
		}
	}
}
