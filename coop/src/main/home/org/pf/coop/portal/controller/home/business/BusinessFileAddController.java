package org.pf.coop.portal.controller.home.business;

import java.security.Principal;

import org.pf.coop.forms.FileUploadForm;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.validators.BusinessFileValidator;
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
@RequestMapping("/home/business/file/add")
public class BusinessFileAddController extends HomeBaseController {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private BusinessFileValidator businessFileValidator;
	
	@GetMapping("/{id}")
	public String uploadBusinessPhoto(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		Business business = this.businessRepo.findByIdAndOwner(id, this.memberRepo.findByMemIdIgnoreCase(principal.getName()));
		
		if (business == null) {
			reat.addFlashAttribute("message", "No such record is found.");
			return "redirect:/home/business/list/current";
		}
		
		FileUploadForm fileUploadForm = new FileUploadForm();
		
		fileUploadForm.setObjectId(business.getId());
		
		model.addAttribute("fileUploadForm", "fileUploadForm");
		model.addAttribute("business", business);
		
		return "home/business/file";
		
	}

	@PostMapping("/*")
	public String uploadBusinessPhoto(@ModelAttribute FileUploadForm fileUploadForm,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		Business business = this.businessRepo.findByIdAndOwner(fileUploadForm.getObjectId(), this.memberRepo.findByMemIdIgnoreCase(principal.getName()));
		
		if (business == null) {
			reat.addFlashAttribute("message", "No such record is found.");
			return "redirect:/home/business/list/current";
		}
		
		this.businessFileValidator.validate(fileUploadForm, result);
		
		if (result.hasErrors()) {
			reat.addFlashAttribute("message", result.getAllErrors());
			return "home/business/file";
		}
		
		try {			

			return "redirect:/home/business/list/current";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home/business/list/current";
		}
	}
}
