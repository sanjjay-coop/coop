package org.pf.coop.portal.controller;

import org.pf.coop.portal.model.SiteLogo;
import org.pf.coop.portal.repository.SiteLogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteLogoController {

	@Autowired
	private SiteLogoRepo siteLogoRepo;

	@GetMapping("/siteLogo")
	public ResponseEntity<byte[]> getSiteLogo() {
	    
		SiteLogo siteLogo = this.siteLogoRepo.getSiteLogo();

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + siteLogo.getFileName() + "\"")
	        .body(siteLogo.getFileData());
	} 
}
