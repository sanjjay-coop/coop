package org.pf.coop.portal.controller.manager.siteLogo;

import java.security.Principal;

import org.pf.coop.forms.FileUploadForm;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.service.SiteLogoService;
import org.pf.coop.portal.validators.SiteLogoValidator;
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
@RequestMapping(value = "/manager/siteLogo")
public class SiteLogoAddController extends ManagerBaseController {
	
	@Autowired
	private SiteLogoService siteLogoService;
	
	@Autowired
	private SiteLogoValidator siteLogoValidator;
	
	@GetMapping
	public String updateSiteLogo(Model model, RedirectAttributes reat, Principal principal) {
		
		FileUploadForm fileUploadForm = new FileUploadForm();
		
		fileUploadForm.setDescription(principal.getName());
		
		model.addAttribute(fileUploadForm);
		
		return "manager/siteLogo/update";
	}
	
	@PostMapping
	public String uploadSiteLogo(@ModelAttribute FileUploadForm fileUploadForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.siteLogoValidator.validate(fileUploadForm, result);
		
		if (result.hasErrors()) {
			return "manager/siteLogo/update";
		}
		
		try {
			this.siteLogoService.addSiteLogo(fileUploadForm.getFile(), principal.getName());
			
			return "redirect:/manager/siteLogo";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			System.out.println(e.getMessage());
			return "redirect:/manager/siteLogo";
		}
	}
}
