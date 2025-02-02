package org.pf.coop.portal.controller.home.member;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentsController  extends HomeBaseController{
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private ReceiptService receiptService;
	
	@GetMapping("/home/payments")
	public String memberProfile(Model model, RedirectAttributes reat, Principal principal) {

		try {
			Member member= this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home";
			}
	
			model.addAttribute("member", member);
			
			return "home/member/payments";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home";
		}
	}
	
	@GetMapping("/home/payment/receipt/{id}")
	public ResponseEntity<byte[]> getReceiptFile(@PathVariable Long id, RedirectAttributes reat, Principal principal) {
	    
		Receipt receipt = (Receipt) this.receiptService.getById(id);
		
		if (receipt==null || !receipt.getMember().getMemId().equals(principal.getName())) {
			String str = "Receipt not found";
			return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "\"")
			        .body(str.getBytes());
		}
		
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + receipt.getFileName() + "\"")
	        .body(receipt.getDocument());
	}
}
