package org.pf.coop.portal.controller.accounts.income;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.accounts.AccountsBaseController;
import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.pf.coop.portal.model.accounts.Income;
import org.pf.coop.portal.repository.accounts.HeadOfAccountRepo;
import org.pf.coop.portal.service.accounts.IncomeService;
import org.pf.coop.portal.validators.accounts.IncomeValidator;
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
@RequestMapping("/accounts/income/edit")
public class IncomeEditController extends AccountsBaseController {

	@Autowired
	private IncomeService incomeService;
	
	@Autowired
	private IncomeValidator incomeValidator;

	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@ModelAttribute("listHeadOfAccount")
	public List<HeadOfAccount> getListHeadOfAccount(){
		return (List<HeadOfAccount>) this.headOfAccountRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
		
	@GetMapping("/{id}")
	public String editIncome(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Income income = (Income) this.incomeService.getById(id);
			
			if (income == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/accounts/income/addNew";
			}
	
			model.addAttribute("income", income);
			
			return "accounts/income/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/accounts/income/addNew";
		}
	}

	@PostMapping("/*")
	public String editIncome(@ModelAttribute Income income,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.incomeValidator.validate(income, result);
		
		if (result.hasErrors()) {
			return "accounts/income/edit";
		}
		
		try {
			TransactionResult tr = this.incomeService.updateIncome(income, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/accounts/income/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/accounts/income/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/accounts/income/edit/"+income.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/accounts/income/edit/"+income.getId();
		}
	}
}
