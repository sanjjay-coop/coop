package org.pf.coop.portal.controller.manager.receipt;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.service.ReceiptService;
import org.pf.coop.portal.validators.ReceiptValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/receipt/addNew")
public class ReceiptAddController extends ManagerBaseController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ReceiptService receiptService;
	
	@Autowired
	private ReceiptValidator receiptValidator;
	
	@GetMapping("/{id}")
	public String receiptAdd(@PathVariable Long id, Model model,
			RedirectAttributes reat) {
		
		try {
			Receipt receipt = new Receipt();
			
			Member member = (Member) this.memberService.getById(id);
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/member/list/current";
			}
			
			receipt.setMember(member);
			
			model.addAttribute(receipt);
			
			return "manager/receipt/addNew";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/member/list/current";
		}
	}
	
	@PostMapping("/*")
	public String receiptAdd(@ModelAttribute Receipt receipt,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.receiptValidator.validate(receipt, result);
		
		if (result.hasErrors()) {
			return "manager/receipt/addNew";
		}
		
		try {
			TransactionResult tr = this.receiptService.addReceipt(receipt, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/member/view/" + receipt.getMember().getId();
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/member/view/" + receipt.getMember().getId();
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/member/view/" + receipt.getMember().getId();
		}
	}
}
