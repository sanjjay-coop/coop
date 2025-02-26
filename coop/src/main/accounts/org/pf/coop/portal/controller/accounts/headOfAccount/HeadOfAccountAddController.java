package org.pf.coop.portal.controller.accounts.headOfAccount;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.accounts.AccountsBaseController;
import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.pf.coop.portal.repository.accounts.HeadOfAccountRepo;
import org.pf.coop.portal.service.accounts.HeadOfAccountService;
import org.pf.coop.portal.validators.accounts.add.HeadOfAccountAddValidator;
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
@RequestMapping(value = "/accounts/headOfAccount/addNew")
public class HeadOfAccountAddController extends AccountsBaseController {

	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@Autowired
	private HeadOfAccountService headOfAccountService;
	
	@Autowired
	private HeadOfAccountAddValidator headOfAccountAddValidator;
	
	@ModelAttribute("listHeadOfAccount")
	public List<HeadOfAccount> getListHeadOfAccount(){
		return (List<HeadOfAccount>) this.headOfAccountRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String headOfAccountAdd(Model model) {
		
		HeadOfAccount headOfAccount = new HeadOfAccount();
		
		model.addAttribute(headOfAccount);
		
		return "accounts/headOfAccount/addNew";
	}
	
	@PostMapping
	public String headOfAccountAdd(@ModelAttribute HeadOfAccount headOfAccount,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.headOfAccountAddValidator.validate(headOfAccount, result);
		
		if (result.hasErrors()) {
			return "accounts/headOfAccount/addNew";
		}
		
		try {
			TransactionResult tr = this.headOfAccountService.addHeadOfAccount(headOfAccount, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "accounts/headOfAccount/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/accounts/headOfAccount/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "accounts/headOfAccount/addNew";
		}
	}
}
