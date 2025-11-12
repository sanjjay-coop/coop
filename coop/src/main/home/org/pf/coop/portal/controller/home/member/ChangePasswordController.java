package org.pf.coop.portal.controller.home.member;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.forms.ChangePasswordForm;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.validators.ChangePasswordFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChangePasswordController extends HomeBaseController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ChangePasswordFormValidator cpfValidator;
	
	@GetMapping("/home/changePassword")
	public String changePassword(Model model) {
		
		ChangePasswordForm changePasswordForm = new ChangePasswordForm();
		
		model.addAttribute(changePasswordForm);
		
		return "home/member/changePassword";
	}
	
	@PostMapping("/home/changePassword")
	public String changePassword(@ModelAttribute ChangePasswordForm changePasswordForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.cpfValidator.validate(changePasswordForm, result);
		
		if (result.hasErrors()) {
			return "home/member/changePassword";
		}
		
		changePasswordForm.setUser(principal.getName());
		
		try {
			TransactionResult tr = this.memberService.changePassword(changePasswordForm, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Password not changed. Please try again later.");
				return "redirect:/home";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "password changed successfully.");
					return "redirect:/home";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/home";
				}
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home";
		}
	}
}
