package org.pf.coop.portal.controller.manager.memberGroup;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.service.MemberGroupService;
import org.pf.coop.portal.validators.edit.MemberGroupEditValidator;
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
@RequestMapping("/manager/memberGroup/edit")
public class MemberGroupEditController extends ManagerBaseController {

	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberGroupService memberGroupService;
	
	@Autowired
	private MemberGroupEditValidator memberGroupEditValidator;
	
	@ModelAttribute("listMemberGroup")
	public List<MemberGroup> getListMemberGroup(){
		return (List<MemberGroup>) this.memberGroupRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/{id}")
	public String editMemberGroup(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			MemberGroup memberGroup = (MemberGroup) this.memberGroupService.getById(id);
			
			if (memberGroup == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/memberGroup/addNew";
			}
	
			model.addAttribute("memberGroup", memberGroup);
			
			return "manager/memberGroup/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/memberGroup/addNew";
		}
	}

	@PostMapping("/*")
	public String editMemberGroup(@ModelAttribute MemberGroup memberGroup,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.memberGroupEditValidator.validate(memberGroup, result);
		
		if (result.hasErrors()) {
			return "manager/memberGroup/edit";
		}
		
		try {
			TransactionResult tr = this.memberGroupService.updateMemberGroup(memberGroup, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/memberGroup/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/memberGroup/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/memberGroup/edit/"+memberGroup.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/memberGroup/edit/"+memberGroup.getId();
		}
	}
}
