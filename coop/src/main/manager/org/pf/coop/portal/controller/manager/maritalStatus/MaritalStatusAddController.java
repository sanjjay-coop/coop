package org.pf.coop.portal.controller.manager.maritalStatus;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.pf.coop.portal.service.MaritalStatusService;
import org.pf.coop.portal.validators.add.MaritalStatusAddValidator;
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
@RequestMapping(value = "/manager/maritalStatus/addNew")
public class MaritalStatusAddController extends ManagerBaseController {

	@Autowired
	private MaritalStatusService maritalStatusService;
	
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	
	@Autowired
	private MaritalStatusAddValidator maritalStatusAddValidator;
	
	@ModelAttribute("listMaritalStatus")
	public List<MaritalStatus> getListMaritalStatus(){
		return (List<MaritalStatus>) this.maritalStatusRepo.findAll(Sort.by(Sort.Direction.ASC, "status"));
	}
	
	@GetMapping
	public String maritalStatusAdd(Model model) {
		
		MaritalStatus maritalStatus = new MaritalStatus();
		
		model.addAttribute(maritalStatus);
		
		return "manager/maritalStatus/addNew";
	}
	
	@PostMapping
	public String maritalStatusAdd(@ModelAttribute MaritalStatus maritalStatus,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.maritalStatusAddValidator.validate(maritalStatus, result);
		
		if (result.hasErrors()) {
			return "manager/maritalStatus/addNew";
		}
		
		try {
			TransactionResult tr = this.maritalStatusService.addMaritalStatus(maritalStatus, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/maritalStatus/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/maritalStatus/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/maritalStatus/addNew";
		}
	}
}
