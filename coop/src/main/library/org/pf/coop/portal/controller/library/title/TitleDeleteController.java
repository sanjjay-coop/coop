package org.pf.coop.portal.controller.library.title;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.library.LibraryBaseController;
import org.pf.coop.portal.service.library.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/library/title/delete")
public class TitleDeleteController extends LibraryBaseController {

	@Autowired
	private TitleService titleService;

	@GetMapping("/{id}")
	public String deleteTitle(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.titleService.deleteTitle(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/library/title/list/current";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/library/title/list/current";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/library/title/list/current";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/library/title/list/current";
			
		}
	}
}
