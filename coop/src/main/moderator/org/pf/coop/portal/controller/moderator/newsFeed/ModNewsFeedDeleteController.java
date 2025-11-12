package org.pf.coop.portal.controller.moderator.newsFeed;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.service.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/newsFeed/delete")
public class ModNewsFeedDeleteController extends ModeratorBaseController {

	@Autowired
	private NewsFeedService newsFeedService;

	@GetMapping("/{id}")
	public String deleteNewsFeed(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.newsFeedService.deleteNewsFeed(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/moderator/newsFeed/list/current";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/moderator/newsFeed/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/newsFeed/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/moderator/newsFeed/list/current";
			
		}
	}
}

