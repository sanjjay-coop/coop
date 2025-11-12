package org.pf.coop.portal.controller.manager.memberGroup;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.service.MemberGroupService;
import org.pf.coop.portal.validators.add.MemberGroupAddValidator;
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
@RequestMapping(value = "/manager/memberGroup/addNew")
public class MemberGroupAddController extends ManagerBaseController {

	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberGroupService memberGroupService;
	
	@Autowired
	private MemberGroupAddValidator memberGroupAddValidator;
	
	@ModelAttribute("listMemberGroup")
	public List<MemberGroup> getListMemberGroup(){
		return (List<MemberGroup>) this.memberGroupRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String memberGroupAdd(Model model) {
		
		MemberGroup memberGroup = new MemberGroup();
		
		model.addAttribute(memberGroup);
		
		return "manager/memberGroup/addNew";
	}
	
	@PostMapping
	public String memberGroupAdd(@ModelAttribute MemberGroup memberGroup,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.memberGroupAddValidator.validate(memberGroup, result);
		
		if (result.hasErrors()) {
			return "manager/memberGroup/addNew";
		}
		
		try {
			TransactionResult tr = this.memberGroupService.addMemberGroup(memberGroup, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/memberGroup/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/memberGroup/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/memberGroup/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/memberGroup/list/current";
		}
	}
}
