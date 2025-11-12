package org.pf.coop.portal.controller.manager.educationLevel;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.service.EducationLevelService;
import org.pf.coop.portal.validators.add.EducationLevelAddValidator;
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
@RequestMapping(value = "/manager/educationLevel/addNew")
public class EducationLevelAddController extends ManagerBaseController {

	@Autowired
	private EducationLevelService educationLevelService;
	
	@Autowired
	private EducationLevelAddValidator educationLevelAddValidator;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@ModelAttribute("listEducationLevel")
	public List<EducationLevel> getListEducationLevel(){
		return (List<EducationLevel>) this.educationLevelRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	@GetMapping
	public String educationLevelAdd(Model model) {
		
		EducationLevel educationLevel = new EducationLevel();
		
		model.addAttribute(educationLevel);
		
		return "manager/educationLevel/addNew";
	}
	
	@PostMapping
	public String educationLevelAdd(@ModelAttribute EducationLevel educationLevel,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.educationLevelAddValidator.validate(educationLevel, result);
		
		if (result.hasErrors()) {
			return "manager/educationLevel/addNew";
		}
		
		try {
			TransactionResult tr = this.educationLevelService.addEducationLevel(educationLevel, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/educationLevel/addNew";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/educationLevel/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/educationLevel/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/educationLevel/addNew";
		}
	}
}
