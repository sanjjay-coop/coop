package org.pf.coop.portal.controller.moderator.gallery;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.service.GalleryService;
import org.pf.coop.portal.validators.edit.GalleryEditValidator;
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
@RequestMapping("/moderator/gallery/edit")
public class GalleryEditController extends ModeratorBaseController {

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private GalleryEditValidator galleryValidator;
	
	@GetMapping("/{id}")
	public String editGallery(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Gallery gallery = (Gallery) this.galleryService.getById(id);
			
			if (gallery == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/gallery/list/current";
			}
	
			model.addAttribute("gallery", gallery);
			
			return "moderator/gallery/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/gallery/list/current";
		}
	}

	@PostMapping("/*")
	public String editGallery(@ModelAttribute Gallery gallery,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.galleryValidator.validate(gallery, result);
		
		if (result.hasErrors()) {
			return "moderator/gallery/edit";
		}
		
		try {
			TransactionResult tr = this.galleryService.updateGallery(gallery, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/gallery/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/gallery/list/current";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/moderator/gallery/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/gallery/list/current";
		}
	}
}
