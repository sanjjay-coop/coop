package org.pf.coop.portal.controller.moderator.carousel;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Carousel;
import org.pf.coop.portal.service.CarouselService;
import org.pf.coop.portal.validators.CarouselValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/moderator/carousel/addNew")
public class CarouselAddController extends ModeratorBaseController {

	@Autowired
	private CarouselService carouselService;
	
	@Autowired
	private CarouselValidator carouselValidator;
	
	@GetMapping
	public String carouselAdd(Model model) {
		
		Carousel carousel = new Carousel();
		
		model.addAttribute(carousel);
		
		return "moderator/carousel/addNew";
	}
	
	@PostMapping
	public String carouselAdd(@ModelAttribute Carousel carousel,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.carouselValidator.validate(carousel, result);
		
		if (result.hasErrors()) {
			return "moderator/carousel/addNew";
		}
		
		try {
			TransactionResult tr = this.carouselService.addCarousel(carousel, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "moderator/carousel/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/moderator/carousel/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "moderator/carousel/addNew";
		}
	}
}
