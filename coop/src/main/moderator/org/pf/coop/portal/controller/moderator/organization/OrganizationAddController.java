package org.pf.coop.portal.controller.moderator.organization;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Organization;
import org.pf.coop.portal.service.OrganizationService;
import org.pf.coop.portal.validators.OrganizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/moderator/organization/addNew")
public class OrganizationAddController extends ModeratorBaseController {

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationValidator organizationValidator;
	
	@GetMapping
	public String organizationAdd(Model model) {
		
		Organization organization = new Organization();
		
		model.addAttribute(organization);
		
		return "moderator/organization/addNew";
	}
	
	@PostMapping
	public String organizationAdd(@ModelAttribute Organization organization,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.organizationValidator.validate(organization, result);
		
		if (result.hasErrors()) {
			return "moderator/organization/addNew";
		}
		
		try {
			TransactionResult tr = this.organizationService.addOrganization(organization, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/moderator/organization/list/current";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/moderator/organization/list";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/moderator/organization/list/current";				
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/organization/list/current";
		}
	}
}
