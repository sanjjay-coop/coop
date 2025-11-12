package org.pf.coop.portal.controller.manager.occupation;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.service.OccupationService;
import org.pf.coop.portal.validators.add.OccupationAddValidator;
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
@RequestMapping(value = "/manager/occupation/addNew")
public class OccupationAddController extends ManagerBaseController {

	@Autowired
	private OccupationService occupationService;
	
	@Autowired
	private OccupationRepo occupationRepo;
	
	@Autowired
	private OccupationAddValidator occupationAddValidator;
	
	@ModelAttribute("listOccupation")
	public List<Occupation> getListOccupation(){
		return (List<Occupation>) this.occupationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String occupationAdd(Model model) {
		
		Occupation occupation = new Occupation();
		
		model.addAttribute(occupation);
		
		return "manager/occupation/addNew";
	}
	
	@PostMapping
	public String occupationAdd(@ModelAttribute Occupation occupation,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.occupationAddValidator.validate(occupation, result);
		
		if (result.hasErrors()) {
			return "manager/occupation/addNew";
		}
		
		try {
			TransactionResult tr = this.occupationService.addOccupation(occupation, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/occupation/addNew";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/occupation/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/occupation/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/occupation/addNew";
		}
	}
}
