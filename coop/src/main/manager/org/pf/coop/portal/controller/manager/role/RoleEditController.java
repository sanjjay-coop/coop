package org.pf.coop.portal.controller.manager.role;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.service.RoleService;
import org.pf.coop.portal.validators.edit.RoleEditValidator;
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
@RequestMapping("/manager/role/edit")
public class RoleEditController extends ManagerBaseController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleEditValidator roleEditValidator;
		
	@GetMapping("/{id}")
	public String editRole(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Role role = (Role) this.roleService.getById(id);
			
			if (role == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/role/addNew";
			}
	
			model.addAttribute("role", role);
			
			return "manager/role/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/role/addNew";
		}
	}

	@PostMapping("/*")
	public String editRole(@ModelAttribute Role role,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.roleEditValidator.validate(role, result);
		
		if (result.hasErrors()) {
			return "manager/role/edit";
		}
		
		try {
			TransactionResult tr = this.roleService.updateRole(role, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/role/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/role/addNew";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/manager/role/addNew";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/role/addNew";
		}
	}
}
