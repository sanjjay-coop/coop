package org.pf.coop.portal.controller.accounts.expenditure;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.accounts.AccountsBaseController;
import org.pf.coop.portal.model.accounts.Expenditure;
import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.pf.coop.portal.repository.accounts.HeadOfAccountRepo;
import org.pf.coop.portal.service.accounts.ExpenditureService;
import org.pf.coop.portal.validators.accounts.ExpenditureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/accounts/expenditure/edit")
public class ExpenditureEditController extends AccountsBaseController {

	@Autowired
	private ExpenditureService expenditureService;
	
	@Autowired
	private ExpenditureValidator expenditureValidator;

	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@ModelAttribute("listHeadOfAccount")
	public List<HeadOfAccount> getListHeadOfAccount(){
		return (List<HeadOfAccount>) this.headOfAccountRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
		
	@GetMapping("/{id}")
	public String editExpenditure(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Expenditure expenditure = (Expenditure) this.expenditureService.getById(id);
			
			if (expenditure == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/accounts/expenditure/addNew";
			}
	
			model.addAttribute("expenditure", expenditure);
			
			return "accounts/expenditure/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/accounts/expenditure/addNew";
		}
	}

	@PostMapping("/*")
	public String editExpenditure(@ModelAttribute Expenditure expenditure,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.expenditureValidator.validate(expenditure, result);
		
		if (result.hasErrors()) {
			return "accounts/expenditure/edit";
		}
		
		try {
			TransactionResult tr = this.expenditureService.updateExpenditure(expenditure, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/accounts/expenditure/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/accounts/expenditure/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/accounts/expenditure/edit/"+expenditure.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/accounts/expenditure/edit/"+expenditure.getId();
		}
	}
}
