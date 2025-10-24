package org.pf.coop.portal.controller.open.gallery;

import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.model.Photo;
import org.pf.coop.portal.service.GalleryService;
import org.pf.coop.service.impl.GalleryFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/open/gallery/view")
public class OpenGalleryViewController extends BaseController {

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	GalleryFileStorageService fileStorageService;
	
	@GetMapping("/{id}")
	public String editGallery(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Gallery gallery = (Gallery) this.galleryService.getById(id);
			
			if (gallery == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/open/gallery/list/current";
			}
	
			Photo photo = new Photo();
			photo.setGallery(gallery);
			
			model.addAttribute("photo", photo);
			model.addAttribute("gallery", gallery);
			
			return "open/gallery/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/open/gallery/list/current";
		}
	}
}
