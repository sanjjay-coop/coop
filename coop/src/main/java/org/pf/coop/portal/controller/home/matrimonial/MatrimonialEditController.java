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
import org.pf.coop.portal.repository.MatrimonialRepo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/matrimonial/edit")
public class MatrimonialEditController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MatrimonialService matrimonialService;
	
	@Autowired
	private MatrimonialRepo matrimonialRepo;

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
		
	@GetMapping("/{id}")
	public String editMatrimonial(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Matrimonial matrimonial = this.matrimonialRepo.getReferenceById(id);
			
			if (matrimonial == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/matrimonial/addNew";
			}
			
			if (!matrimonial.getOwner().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/home";
			}
	
			model.addAttribute("matrimonial", matrimonial);
			
			return "home/matrimonial/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/matrimonial/addNew";
		}
	}

	@PostMapping("/*")
	public String editMatrimonial(@ModelAttribute Matrimonial matrimonial,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.matrimonialValidator.validate(matrimonial, result);
		
		if (result.hasErrors()) {
			return "home/matrimonial/edit";
		}
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Matrimonial emp = this.matrimonialRepo.getReferenceById(matrimonial.getId());
			

			if (!emp.getOwner().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "Record is owned by other Member.");
				return "redirect:/home";
			}
			
			matrimonial.setOwner(member);
			
			TransactionResult tr = this.matrimonialService.updateMatrimonial(matrimonial, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/home/matrimonial/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/home/matrimonial/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/home/matrimonial/edit/"+matrimonial.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home/matrimonial/edit/"+matrimonial.getId();
		}
	}
}
