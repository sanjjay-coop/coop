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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/quotation/edit")
public class ModQuotationEditController extends ModeratorBaseController {

	@Autowired
	private QuotationService quotationService;
	
	@Autowired
	private QuotationValidator quotationValidator;
	
	@GetMapping("/{id}")
	public String editQuotation(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Quotation quotation = (Quotation) this.quotationService.getById(id);
			
			if (quotation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/quotation/addNew";
			}
	
			model.addAttribute("quotation", quotation);
			
			return "moderator/quotation/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/quotation/addNew";
		}
	}

	@PostMapping("/*")
	public String editQuotation(@ModelAttribute Quotation quotation,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.quotationValidator.validate(quotation, result);
		
		if (result.hasErrors()) {
			return "moderator/quotation/edit";
		}
		
		try {
			TransactionResult tr = this.quotationService.updateQuotation(quotation, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/quotation/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/quotation/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/moderator/quotation/edit/"+quotation.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/quotation/edit/"+quotation.getId();
		}
	}
}
