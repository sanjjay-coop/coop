package org.pf.coop.portal.controller.manager.advert;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Advert;
import org.pf.coop.portal.service.AdvertService;
import org.pf.coop.portal.validators.AdvertValidator;
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
@RequestMapping(value = "/manager/advert/addNew")
public class AdvertAddController extends ManagerBaseController {

	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private AdvertValidator advertValidator;
	
	@GetMapping
	public String advertAdd(Model model) {
		
		Advert advert = new Advert();
		
		model.addAttribute(advert);
		
		return "manager/advert/addNew";
	}
	
	@PostMapping
	public String advertAdd(@ModelAttribute Advert advert,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.advertValidator.validate(advert, result);
		
		if (result.hasErrors()) {
			return "manager/advert/addNew";
		}
		
		try {
			TransactionResult tr = this.advertService.addAdvert(advert, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/advert/list/current";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/advert/list/current";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/advert/list/current";
		}
	}
}
