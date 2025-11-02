package org.pf.coop.portal.controller.open.document;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Document;
import org.pf.coop.portal.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OpenDocumentFileController extends HomeBaseController {

	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/open/document/file/{id}")
	public ResponseEntity<byte[]> documentFileDownload(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		
		Document obj = (Document) this.documentService.getById(id);
			
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + obj.getFileName() + "\"")
		        .body(obj.getDocument());
	}
}
