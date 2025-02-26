package org.pf.coop.portal.controller.moderator.bulkEmail;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.service.BulkEmailService;
import org.pf.coop.portal.validators.add.BulkEmailAddValidator;
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
@RequestMapping(value = "/moderator/bulkEmail/addNew")
public class BulkEmailAddController extends ModeratorBaseController {

	@Autowired
	private BulkEmailService bulkEmailService;
	
	@Autowired
	private BulkEmailAddValidator bulkEmailAddValidator;
	
	@GetMapping
	public String bulkEmailAdd(Model model) {
		
		BulkEmail bulkEmail = new BulkEmail();
		
		model.addAttribute(bulkEmail);
		
		return "moderator/bulkEmail/addNew";
	}
	
	@PostMapping
	public String bulkEmailAdd(@ModelAttribute BulkEmail bulkEmail,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.bulkEmailAddValidator.validate(bulkEmail, result);
		
		if (result.hasErrors()) {
			return "moderator/bulkEmail/addNew";
		}
		
		try {
			Parameters params = this.getParameters();
			
			int dayLimit = params.getEmailDayLimit();
			
			TransactionResult tr = this.bulkEmailService.addBulkEmail(dayLimit, bulkEmail, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/moderator/bulkEmail/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/moderator/bulkEmail/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/bulkEmail/list";
		}
	}
}