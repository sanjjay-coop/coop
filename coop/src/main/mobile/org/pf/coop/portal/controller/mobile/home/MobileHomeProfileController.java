package org.pf.coop.portal.controller.mobile.home;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/home/profile")
public class MobileHomeProfileController extends MobileBaseController {

	@Autowired
	MemberRepo memberRepo;
	
	@GetMapping
	public String getProfile(Model model, RedirectAttributes reat, Principal principal) {
		
		Member member= this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		if (member == null) {
			reat.addFlashAttribute("message", "No such record.");
			return "redirect:/mobile/index";
		}

		model.addAttribute("member", member);
		
		return "mobile/home/member/profile";
	}
}
