package org.pf.coop.portal.controller.manager.memberType;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.service.MemberTypeService;
import org.pf.coop.portal.validators.edit.MemberTypeEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/manager/memberType/edit")
public class MemberTypeEditController extends ManagerBaseController {

	@Autowired
	private MemberTypeService memberTypeService;
	
	@Autowired
	private MemberTypeEditValidator memberTypeEditValidator;
		
	@GetMapping("/{id}")
	public String editMemberType(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			MemberType memberType = (MemberType) this.memberTypeService.getById(id);
			
			if (memberType == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/memberType/addNew";
			}
	
			model.addAttribute("memberType", memberType);
			
			return "manager/memberType/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/memberType/addNew";
		}
	}

	@PostMapping("/*")
	public String editMemberType(@ModelAttribute MemberType memberType,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.memberTypeEditValidator.validate(memberType, result);
		
		if (result.hasErrors()) {
			return "manager/memberType/edit";
		}
		
		try {
			TransactionResult tr = this.memberTypeService.updateMemberType(memberType, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/memberType/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/memberType/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/memberType/edit/"+memberType.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/memberType/edit/"+memberType.getId();
		}
	}
}
