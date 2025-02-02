package org.pf.coop.portal.controller.manager.member;

import java.security.Principal;
import java.util.List;

import org.pf.coop.forms.SimpleSearchForm;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
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

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberSearchController extends ManagerBaseController {

	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	SimpleSearchFormValidator ssfValidator;
	
	@GetMapping("/manager/member/search")
	public String searchMember(Model model) {
		
		SimpleSearchForm ssf = new SimpleSearchForm();
		
		model.addAttribute("simpleSearchForm", ssf);
		
		return "manager/member/search";
	}
	
	@PostMapping("/manager/member/search")
	public String searchMember(@ModelAttribute SimpleSearchForm ssf,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		request.getSession().setAttribute("listMember_type", "search");
		request.getSession().setAttribute("searchMemberForm", ssf);
		
		this.ssfValidator.validate(ssf, result);
		
		if (result.hasErrors()) {
			return "manager/member/search";
		}
		
		List<Member> listMember = this.memberRepo.listMemberSearch(ssf.getSearchString().toLowerCase());
		
		if (listMember.isEmpty()) {
			reat.addFlashAttribute("message", "No result found.");
			return "redirect:/manager/member/search";
		}
		
		model.addAttribute("listMember", listMember);
				
		return "manager/member/searchResult";
	}
	
	@GetMapping("/manager/member/search/list")
	public String searchMemberResult(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		request.getSession().setAttribute("listMember_type", "search");
		
		SimpleSearchForm ssf = (SimpleSearchForm) request.getSession().getAttribute("searchMemberForm");
		
		if (ssf == null) {
			return "redirect:/manager/member/search";
		}
		
		if (ssf.getClass()==null) {
			return "manager/member/search";
		}
		
		List<Member> listMember = this.memberRepo.listMemberSearch(ssf.getSearchString().toLowerCase());
		
		if (listMember.isEmpty()) {
			reat.addFlashAttribute("message", "No result found.");
			return "redirect:/manager/member/search";
		}
		
		model.addAttribute("listMember", listMember);
				
		return "manager/member/searchResult";
	}
}
