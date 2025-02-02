package org.pf.coop.portal.controller.home.title;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.library.CheckOut;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.library.CheckOutRepo;
import org.pf.coop.portal.service.library.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/title/view")
public class HomeTitleViewController extends HomeBaseController {

	@Autowired
	private TitleService titleService;

	@Autowired
	private CheckOutRepo checkOutRepo;
	
	@GetMapping("/{id}")
	public String viewTitle(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Title title = (Title) this.titleService.getById(id);
			
			if (title == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/title/search";
			}
			
			CheckOut co = this.checkOutRepo.findByTitle(title);
	
			model.addAttribute("title", title);
			model.addAttribute("co", co);
			
			return "home/title/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/title/search";
		}
	}
}
