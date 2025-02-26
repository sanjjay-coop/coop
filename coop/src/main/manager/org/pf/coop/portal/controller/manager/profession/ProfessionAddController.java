package org.pf.coop.portal.controller.manager.profession;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Profession;
import org.pf.coop.portal.repository.ProfessionRepo;
import org.pf.coop.portal.service.ProfessionService;
import org.pf.coop.portal.validators.add.ProfessionAddValidator;
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
@RequestMapping(value = "/manager/profession/addNew")
public class ProfessionAddController extends ManagerBaseController {

	@Autowired
	private ProfessionService professionService;
	
	@Autowired
	private ProfessionRepo professionRepo;
	
	@Autowired
	private ProfessionAddValidator professionAddValidator;
	
	@ModelAttribute("listProfession")
	public List<Profession> getListProfession(){
		return (List<Profession>) this.professionRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String professionAdd(Model model) {
		
		Profession profession = new Profession();
		
		model.addAttribute(profession);
		
		return "manager/profession/addNew";
	}
	
	@PostMapping
	public String professionAdd(@ModelAttribute Profession profession,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.professionAddValidator.validate(profession, result);
		
		if (result.hasErrors()) {
			return "manager/profession/addNew";
		}
		
		try {
			TransactionResult tr = this.professionService.addProfession(profession, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/profession/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/profession/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/profession/addNew";
		}
	}
}
