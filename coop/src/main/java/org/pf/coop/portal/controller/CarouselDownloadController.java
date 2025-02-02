package org.pf.coop.portal.controller;

import java.security.Principal;

import org.pf.coop.portal.model.Carousel;
import org.pf.coop.portal.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CarouselDownloadController {

	@Autowired
	private CarouselService carouselService;

	@GetMapping("/carousel/download/{id}")
	public ResponseEntity<byte[]> getCarousel(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {
	    
		Carousel carousel = (Carousel) this.carouselService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + carousel.getFileName() + "\"")
	        .body(carousel.getFileData());
	} 
}
