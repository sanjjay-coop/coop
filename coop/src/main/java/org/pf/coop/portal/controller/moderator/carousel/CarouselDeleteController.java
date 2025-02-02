package org.pf.coop.portal.controller.moderator.carousel;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/carousel/delete")
public class CarouselDeleteController extends ModeratorBaseController {

	@Autowired
	private CarouselService carouselService;

	@GetMapping("/{id}")
	public String deleteCarousel(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.carouselService.deleteCarousel(id, principal.getName());
			
			if (tr == null ) {
				reat.addFlashAttribute("message", "Record could not be deleted.");
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record deleted successfully.");
			} else {
				reat.addFlashAttribute("message", "Record could not be deleted.");
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
		}

		return "redirect:/moderator/carousel/list/current";
	}
}

