package org.pf.coop.portal.controller;

import java.security.Principal;

import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;

@Controller
public class LoginController extends IndexBaseController {
	
	@Autowired
	MemberRepo memberRepo;
	
	@GetMapping("/login")
	public String loginView(Model model) {
		
		return "login";
	}
	
	@GetMapping("/logout-success")
	public String logoutSuccessView(Model model, RedirectAttributes reat) {
		
		reat.addFlashAttribute("message", "You have successfully signed out.");
		return "redirect:/";
	}
	
	@GetMapping("/login-success")
	public String loginSuccessView(Model model, RedirectAttributes reat, Principal principal) {
		
		reat.addFlashAttribute("message", "Welcome!.");
		
		return "redirect:/home";
	}
}
