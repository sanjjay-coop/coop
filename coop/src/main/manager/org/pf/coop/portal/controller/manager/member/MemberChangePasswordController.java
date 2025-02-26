package org.pf.coop.portal.controller.manager.member;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/member/changePassword")
public class MemberChangePasswordController  extends ManagerBaseController{
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/{id}")
	public String memberChangePassword(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Member member = (Member) this.memberService.getById(id);
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/member/addNew";
			}
	
			model.addAttribute("member", member);
			
			return "manager/member/changePassword";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/member/addNew";
		}
	}
	
	@PostMapping("/*")
	public String resetPassword(@ModelAttribute Member member, Model model, RedirectAttributes reat, Principal principal) {
		
		try {
			
			member.setPassword(member.getRetypePassword());
			
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
			
			return "redirect:/manager/member/view/"+member.getId();
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/member/view/"+member.getId();
		}
	}
}
