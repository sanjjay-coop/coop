package org.pf.coop.portal.controller.home.member;

import java.security.Principal;
import java.util.List;

import org.pf.coop.forms.SimpleSearchForm;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.validators.SimpleSearchFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeMemberSearchController extends HomeBaseController {

	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	SimpleSearchFormValidator ssfValidator;
	
	@GetMapping("/home/member/search")
	public String searchMember(Model model) {
		
		SimpleSearchForm ssf = new SimpleSearchForm();
		
		model.addAttribute("simpleSearchForm", ssf);
		
		return "home/member/search";
	}
	
	@PostMapping("/home/member/search")
	public String searchMember(@ModelAttribute SimpleSearchForm ssf,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.ssfValidator.validate(ssf, result);
		
		if (result.hasErrors()) {
			return "home/member/search";
		}
		
		List<Member> listMember = this.memberRepo.listMemberSearch(ssf.getSearchString().toLowerCase());
		
		if (listMember.isEmpty()) {
			reat.addFlashAttribute("message", "No result found.");
			return "redirect:/home/member/search";
		}
		
		model.addAttribute("listMember", listMember);
				
		return "home/member/searchResult";
	}
}
