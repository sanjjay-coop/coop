package org.pf.coop.portal.controller.manager.member;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/member/view")
public class MemberViewController extends ManagerBaseController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/{id}")
	public String editMember(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Member member = (Member) this.memberService.getById(id);
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/member/addNew";
			}
	
			model.addAttribute("member", member);
			
			return "manager/member/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/member/addNew";
		}
	}
}
