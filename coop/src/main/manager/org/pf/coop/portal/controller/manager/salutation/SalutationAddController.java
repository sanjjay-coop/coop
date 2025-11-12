package org.pf.coop.portal.controller.manager.salutation;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.SalutationRepo;
import org.pf.coop.portal.service.SalutationService;
import org.pf.coop.portal.validators.add.SalutationAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/salutation/addNew")
public class SalutationAddController extends ManagerBaseController {

	@Autowired
	private SalutationService salutationService;
	
	@Autowired
	private SalutationRepo salutationRepo;
	
	@Autowired
	private SalutationAddValidator salutationAddValidator;
	
	@ModelAttribute("listSalutation")
	public List<Salutation> getListSalutation(){
		return (List<Salutation>) this.salutationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String salutationAdd(Model model) {
		
		Salutation salutation = new Salutation();
		
		model.addAttribute(salutation);
		
		return "manager/salutation/addNew";
	}
	
	@PostMapping
	public String salutationAdd(@ModelAttribute Salutation salutation,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.salutationAddValidator.validate(salutation, result);
		
		if (result.hasErrors()) {
			return "manager/salutation/addNew";
		}
		
		try {
			TransactionResult tr = this.salutationService.addSalutation(salutation, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/salutation/addNew";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/salutation/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/salutation/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/salutation/addNew";
		}
	}
}
