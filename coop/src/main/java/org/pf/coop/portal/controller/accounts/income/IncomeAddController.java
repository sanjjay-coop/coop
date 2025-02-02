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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/accounts/income/addNew")
public class IncomeAddController extends AccountsBaseController {
	
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
		
	@GetMapping
	public String incomeAdd(Model model) {
		
		Income income = new Income();
		
		model.addAttribute(income);
		
		return "accounts/income/addNew";
	}
	
	@PostMapping
	public String incomeAdd(@ModelAttribute Income income,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.incomeValidator.validate(income, result);
		
		if (result.hasErrors()) {
			return "accounts/income/addNew";
		}
		
		try {
			TransactionResult tr = this.incomeService.addIncome(income, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "accounts/income/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/accounts/income/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "accounts/income/addNew";
		}
	}
}
