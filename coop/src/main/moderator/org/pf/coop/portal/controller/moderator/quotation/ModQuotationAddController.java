package org.pf.coop.portal.controller.moderator.quotation;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.service.QuotationService;
import org.pf.coop.portal.validators.QuotationValidator;
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
@RequestMapping(value = "/moderator/quotation/addNew")
public class ModQuotationAddController extends ModeratorBaseController {

	@Autowired
	private QuotationService quotationService;
	
	@Autowired
	private QuotationValidator quotationValidator;
	
	@GetMapping
	public String quotationAdd(Model model) {
		
		Quotation quotation = new Quotation();
		
		model.addAttribute(quotation);
		
		return "moderator/quotation/addNew";
	}
	
	@PostMapping
	public String quotationAdd(@ModelAttribute Quotation quotation,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.quotationValidator.validate(quotation, result);
		
		if (result.hasErrors()) {
			return "moderator/quotation/addNew";
		}
		
		try {
			TransactionResult tr = this.quotationService.addQuotation(quotation, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/moderator/quotation/list/current";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/moderator/quotation/list/current";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/quotation/list/current";
		}
	}
}
