package org.pf.coop.portal.controller.manager.gender;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.service.GenderService;
import org.pf.coop.portal.validators.add.GenderAddValidator;
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
@RequestMapping(value = "/manager/gender/addNew")
public class GenderAddController extends ManagerBaseController {

	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private GenderService genderService;
	
	@Autowired
	private GenderAddValidator genderAddValidator;
	
	@ModelAttribute("listGender")
	public List<Gender> getListGender(){
		return (List<Gender>) this.genderRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String genderAdd(Model model) {
		
		Gender gender = new Gender();
		
		model.addAttribute(gender);
		
		return "manager/gender/addNew";
	}
	
	@PostMapping
	public String genderAdd(@ModelAttribute Gender gender,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.genderAddValidator.validate(gender, result);
		
		if (result.hasErrors()) {
			return "manager/gender/addNew";
		}
		
		try {
			TransactionResult tr = this.genderService.addGender(gender, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/gender/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/gender/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/gender/addNew";
		}
	}
}
