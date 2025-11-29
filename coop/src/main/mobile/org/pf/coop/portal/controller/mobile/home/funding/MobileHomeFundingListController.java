package org.pf.coop.portal.controller.mobile.home.funding;

import java.security.Principal;
import java.util.List;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.FundingRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mobile/home/funding")
public class MobileHomeFundingListController extends MobileBaseController {
	
	@Autowired
	private FundingRepo fundingRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/list")
	public String listFunding(Model model, Principal principal, HttpServletRequest request) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());

		List<Funding> listFunding = this.fundingRepo.findByApplicant(member, Sort.by(Sort.Direction.DESC, "applicationDate"));
		
		model.addAttribute("listFunding", listFunding);
		
		return "mobile/home/funding/list";
	}
}
