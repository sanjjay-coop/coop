package org.pf.coop.portal.controller.home.matrimonial;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.service.MatrimonialService;
import org.pf.coop.portal.validators.MatrimonialValidator;
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
@RequestMapping(value = "/home/matrimonial/addNew")
public class MatrimonialAddController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MatrimonialService matrimonialService;

	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private OccupationRepo occupationRepo;

	@Autowired
	private MatrimonialValidator matrimonialValidator;
	
	@ModelAttribute("listEducationLevel")
	public List<EducationLevel> getListEducationLevel(){
		return (List<EducationLevel>) this.educationLevelRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listOccupation")
	public List<Occupation> getListOccupation(){
		return (List<Occupation>) this.occupationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String matrimonialAdd(Model model) {
		
		Matrimonial matrimonial = new Matrimonial();
		
		model.addAttribute(matrimonial);
		
		return "home/matrimonial/addNew";
	}
	
	@PostMapping
	public String matrimonialAdd(@ModelAttribute Matrimonial matrimonial,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		matrimonial.setOwner(member);
		
		this.matrimonialValidator.validate(matrimonial, result);
		
		if (result.hasErrors()) {
			return "home/matrimonial/addNew";
		}
		
		try {
			TransactionResult tr = this.matrimonialService.addMatrimonial(matrimonial, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "home/matrimonial/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/home/matrimonial/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "home/matrimonial/addNew";
		}
	}
}
