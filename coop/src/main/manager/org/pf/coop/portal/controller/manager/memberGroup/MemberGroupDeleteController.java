package org.pf.coop.portal.controller.manager.memberGroup;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.service.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/memberGroup/delete")
public class MemberGroupDeleteController extends ManagerBaseController {

	@Autowired
	private MemberGroupService memberGroupService;

	@GetMapping("/{id}")
	public String deleteMemberGroup(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.memberGroupService.deleteMemberGroup(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/manager/memberGroup/list/current";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/manager/memberGroup/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", tr.getMessage());
				return "redirect:/manager/memberGroup/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/manager/memberGroup/list/current";
			
		}
	}
}

