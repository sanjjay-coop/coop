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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/newsFeed/edit")
public class ModNewsFeedEditController extends ModeratorBaseController {

	@Autowired
	private NewsFeedService newsFeedService;
	
	@Autowired
	private NewsFeedValidator newsFeedValidator;
	
	@GetMapping("/{id}")
	public String editNewsFeed(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			NewsFeed newsFeed = (NewsFeed) this.newsFeedService.getById(id);
			
			if (newsFeed == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/newsFeed/list/current";
			}
	
			model.addAttribute("newsFeed", newsFeed);
			
			return "moderator/newsFeed/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/newsFeed/list/current";
		}
	}

	@PostMapping("/*")
	public String editNewsFeed(@ModelAttribute NewsFeed newsFeed,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.newsFeedValidator.validate(newsFeed, result);
		
		if (result.hasErrors()) {
			return "moderator/newsFeed/edit";
		}
		
		try {
			TransactionResult tr = this.newsFeedService.updateNewsFeed(newsFeed, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/newsFeed/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/newsFeed/list/current";
				} else {
					reat.addFlashAttribute("message", "Error: " + tr.getMessage());
					return "redirect:/moderator/newsFeed/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/newsFeed/list/current";
		}
	}
}
