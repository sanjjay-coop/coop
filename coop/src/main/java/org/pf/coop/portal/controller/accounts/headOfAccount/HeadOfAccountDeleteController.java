package org.pf.coop.portal.controller.accounts.headOfAccount;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.accounts.AccountsBaseController;
import org.pf.coop.portal.service.accounts.HeadOfAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts/headOfAccount/delete")
public class HeadOfAccountDeleteController extends AccountsBaseController {

	@Autowired
	private HeadOfAccountService headOfAccountService;

	@GetMapping("/{id}")
	public String deleteHeadOfAccount(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.headOfAccountService.deleteHeadOfAccount(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/accounts/headOfAccount/addNew";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/accounts/headOfAccount/addNew";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/accounts/headOfAccount/addNew";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/accounts/headOfAccount/addNew";
			
		}
	}
}
