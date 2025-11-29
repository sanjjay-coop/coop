package org.pf.coop.portal.controller.mobile.home.funding;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/home/funding/view")
public class MobileHomeFundingViewController extends MobileBaseController {
	
	@Autowired
	private FundingService fundingService;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/{id}")
	public String viewFundingRequest(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());

		try {
			Funding funding = (Funding) this.fundingService.getById(id);
			
			if (funding == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/mobile/home/funding/list";
			}
			
			if (!funding.getApplicant().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/mobile/home/funding/list";				
			}
	
			model.addAttribute("funding", funding);
			
			return "mobile/home/funding/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/mobile/home/funding/list";
		}
	}
}
