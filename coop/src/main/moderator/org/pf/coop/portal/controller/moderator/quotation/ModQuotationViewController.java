package org.pf.coop.portal.controller.moderator.quotation;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/quotation/view")
public class ModQuotationViewController extends ModeratorBaseController {

	@Autowired
	private QuotationService quotationService;
	
	@GetMapping("/{id}")
	public String editQuotation(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Quotation quotation = (Quotation) this.quotationService.getById(id);
			
			if (quotation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/quotation/list/current";
			}
	
			model.addAttribute("quotation", quotation);
			
			return "moderator/quotation/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/quotation/list/current";
		}
	}
}