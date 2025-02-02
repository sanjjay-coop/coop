package org.pf.coop.portal.controller;

import java.security.Principal;

import org.pf.coop.common.RandomString;
import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResetPasswordController extends IndexBaseController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/resetPassword")
	public String resetPassword(Model model, RedirectAttributes reat, Principal principal) {
		
		if (principal != null) {
			reat.addFlashAttribute("message", "You are already logged-in. Use change password option.");
			return "redirect:/home/changePassword";
		}
		
		Member member = new Member();
		
		model.addAttribute("member", member);
		
		return "resetPassword";
		
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute Member member, Model model, RedirectAttributes reat, Principal principal) {
		
		if (principal != null) {
			reat.addFlashAttribute("message", "You are already logged-in. Use change password option.");
			return "redirect:/home/changePassword";
		}
		
		Member obj = memberRepo.findByMemIdIgnoreCase(member.getMemId());
		
		if (obj == null) {
			reat.addFlashAttribute("message", "Member record does not exist.");
			return "redirect:/resetPassword";
		}
		
		if (!(member.getEmail().equals(obj.getEmail()) && member.getMobile().equals(obj.getMobile()))) {
			reat.addFlashAttribute("message", "Member record does not exist.");
			return "redirect:/resetPassword";
		}
		
		RandomString rb = new RandomString();
		
		String strPass = rb.getAlphaNumericString(6);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		obj.setPassword(passwordEncoder.encode(strPass));
		
		try {
			TransactionResult tr = this.memberService.resetPassword(member);
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Password not changed. Please try again later.");
				return "home/member/changePassword";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "password changed successfully.");
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
				}
			}
			
			return "redirect:/home";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home/changePassword";
		}
	}
}
