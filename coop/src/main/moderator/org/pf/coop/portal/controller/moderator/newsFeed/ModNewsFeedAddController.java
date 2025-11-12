package org.pf.coop.portal.controller.moderator.newsFeed;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.NewsFeed;
import org.pf.coop.portal.service.NewsFeedService;
import org.pf.coop.portal.validators.NewsFeedValidator;
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
@RequestMapping(value = "/moderator/newsFeed/addNew")
public class ModNewsFeedAddController extends ModeratorBaseController {

	@Autowired
	private NewsFeedService newsFeedService;
	
	@Autowired
	private NewsFeedValidator newsFeedValidator;
	
	@GetMapping
	public String newsFeedAdd(Model model) {
		
		NewsFeed newsFeed = new NewsFeed();
		
		model.addAttribute(newsFeed);
		
		return "moderator/newsFeed/addNew";
	}
	
	@PostMapping
	public String newsFeedAdd(@ModelAttribute NewsFeed newsFeed,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.newsFeedValidator.validate(newsFeed, result);
		
		if (result.hasErrors()) {
			return "moderator/newsFeed/addNew";
		}
		
		try {
			TransactionResult tr = this.newsFeedService.addNewsFeed(newsFeed, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/moderator/newsFeed/list/current";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/moderator/newsFeed/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/newsFeed/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/newsFeed/list/current";
		}
	}
}
