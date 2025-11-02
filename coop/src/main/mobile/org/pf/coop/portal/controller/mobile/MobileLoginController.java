package org.pf.coop.portal.controller.mobile;

import java.security.Principal;
import java.util.List;

import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;

@Controller
public class MobileLoginController extends MobileBaseController {

	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	QuotationRepo quotationRepo;
	
	@ModelAttribute("loginQuotation")
	public Quotation getLoginQuotation(){
		List<Quotation> listQuotation = this.quotationRepo.findRandomQuotation();
		
		if (listQuotation.isEmpty()) return null;
		return listQuotation.get(0);
	}
	
	@GetMapping("/mobile/home")
	public String homeView(Model model) {
		
		return "mobile/home/default";
	}
	
	@GetMapping("/mobile/login")
	public String loginView(Model model) {
		
		return "mobile/login";
	}
	
	@GetMapping("/mobile/logout-success")
	public String logoutSuccessView(Model model, RedirectAttributes reat) {
		
		reat.addFlashAttribute("message", "You have successfully signed out.");
		return "redirect:/mobile/open/index";
	}
	
	@GetMapping("/mobile/login-success")
	public String loginSuccessView(Model model, RedirectAttributes reat, Principal principal) {
		
		reat.addFlashAttribute("message", "Welcome!.");
		
		return "redirect:/mobile/open/index";
	}
}
