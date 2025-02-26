package org.pf.coop.portal.controller.manager.receipt;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReceiptDeleteController extends ManagerBaseController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ReceiptService receiptService;
	
	@GetMapping("/manager/member/{memId}/receipt/delete/{id}")
	public String receiptDelete(@PathVariable Long memId, @PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		try {
			Receipt receipt = (Receipt) this.receiptService.getById(id);
			
			Member member = (Member) this.memberService.getById(memId);
			
			if (receipt == null) {
				
				if (member == null) {
					reat.addFlashAttribute("message", "No such record.");
					return "redirect:/manager/member/list/current";
				} else {
					reat.addFlashAttribute("message", "No such record.");
					return "redirect:/manager/member/view/" + member.getId();
				}
			}
			
			if (!receipt.getMember().getId().equals(member.getId())) {
				reat.addFlashAttribute("message", "No matching record available.");
				return "redirect:/manager/member/view/" + member.getId();
			}
			
			TransactionResult tr = this.receiptService.deleteReceipt(receipt.getId(), principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			}
			
			return "redirect:/manager/member/view/"+member.getId();
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/member/list/current";
		}
	}
}
