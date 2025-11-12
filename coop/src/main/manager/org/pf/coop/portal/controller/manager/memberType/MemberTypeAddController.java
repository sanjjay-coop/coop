package org.pf.coop.portal.controller.manager.memberType;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.service.MemberTypeService;
import org.pf.coop.portal.validators.add.MemberTypeAddValidator;
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
@RequestMapping(value = "/manager/memberType/addNew")
public class MemberTypeAddController extends ManagerBaseController {

	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Autowired
	private MemberTypeService memberTypeService;
	
	@Autowired
	private MemberTypeAddValidator memberTypeAddValidator;
	
	@ModelAttribute("listMemberType")
	public List<MemberType> getListMemberType(){
		return (List<MemberType>) this.memberTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String memberTypeAdd(Model model) {
		
		MemberType memberType = new MemberType();
		
		model.addAttribute(memberType);
		
		return "manager/memberType/addNew";
	}
	
	@PostMapping
	public String memberTypeAdd(@ModelAttribute MemberType memberType,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.memberTypeAddValidator.validate(memberType, result);
		
		if (result.hasErrors()) {
			return "manager/memberType/addNew";
		}
		
		try {
			TransactionResult tr = this.memberTypeService.addMemberType(memberType, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/memberType/addNew";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/memberType/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/memberType/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/memberType/addNew";
		}
	}
}
