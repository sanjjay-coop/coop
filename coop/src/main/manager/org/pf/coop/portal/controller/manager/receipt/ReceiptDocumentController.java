package org.pf.coop.portal.controller.manager.receipt;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReceiptDocumentController extends ManagerBaseController {

	@Autowired
	private ReceiptService receiptService;
	
	@GetMapping("/receipt/document/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
	    
		Receipt receipt = (Receipt) this.receiptService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + receipt.getFileName() + "\"")
	        .body(receipt.getDocument());
	}
}
