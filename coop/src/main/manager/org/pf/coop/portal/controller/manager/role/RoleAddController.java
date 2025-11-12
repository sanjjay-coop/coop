package org.pf.coop.portal.controller.manager.role;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.repository.RoleRepo;
import org.pf.coop.portal.service.RoleService;
import org.pf.coop.portal.validators.add.RoleAddValidator;
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
@RequestMapping(value = "/manager/role/addNew")
public class RoleAddController extends ManagerBaseController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private RoleAddValidator roleAddValidator;
	
	@ModelAttribute("listRole")
	public List<Role> getListRole(){
		return (List<Role>) this.roleRepo.findAll(Sort.by(Sort.Direction.ASC, "code"));
	}
	
	@GetMapping
	public String roleAdd(Model model) {
		
		Role role = new Role();
		
		model.addAttribute(role);
		
		return "manager/role/addNew";
	}
	
	@PostMapping
	public String roleAdd(@ModelAttribute Role role,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.roleAddValidator.validate(role, result);
		
		if (result.hasErrors()) {
			return "manager/role/addNew";
		}
		
		try {
			TransactionResult tr = this.roleService.addRole(role, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/role/addNew";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/role/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/role/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/role/addNew";
		}
	}
}
