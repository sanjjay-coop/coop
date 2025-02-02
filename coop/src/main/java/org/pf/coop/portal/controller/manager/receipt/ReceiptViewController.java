package org.pf.coop.portal.controller.manager.receipt;

import java.security.Principal;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReceiptViewController extends ManagerBaseController {
	
	@Autowired
	private ReceiptService receiptService;
	
	@GetMapping("/manager/payment/receipt/{id}")
	public ResponseEntity<byte[]> getReceiptFile(@PathVariable Long id, RedirectAttributes reat, Principal principal) {
	    
		Receipt receipt = (Receipt) this.receiptService.getById(id);
		
		if (receipt==null) {
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
