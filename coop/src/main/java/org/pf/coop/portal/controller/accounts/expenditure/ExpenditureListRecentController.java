package org.pf.coop.portal.controller.accounts.expenditure;

import java.security.Principal;
import java.util.List;

import org.pf.coop.portal.controller.accounts.AccountsBaseController;
import org.pf.coop.portal.model.accounts.Expenditure;
import org.pf.coop.portal.repository.accounts.ExpenditureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpenditureListRecentController extends AccountsBaseController {

	@Autowired
	private ExpenditureRepo expenditureRepo;

	@GetMapping("/accounts/expenditure/listRecent")
	public String listRecent(Model model, RedirectAttributes reat, Principal principal) {

		List<Expenditure> listExpenditure = this.expenditureRepo.listExpenditureRecent();
		
		model.addAttribute("listExpenditure", listExpenditure);
		
		return "accounts/expenditure/listRecent";
	}
}
