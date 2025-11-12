package org.pf.coop.portal.controller.moderator.holiday;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Holiday;
import org.pf.coop.portal.service.HolidayService;
import org.pf.coop.portal.validators.HolidayValidator;
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
@RequestMapping(value = "/moderator/holiday/addNew")
public class ModHolidayAddController extends ModeratorBaseController {

	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private HolidayValidator holidayValidator;
	
	@GetMapping
	public String holidayAdd(Model model) {
		
		Holiday holiday = new Holiday();
		
		model.addAttribute(holiday);
		
		return "moderator/holiday/addNew";
	}
	
	@PostMapping
	public String holidayAdd(@ModelAttribute Holiday holiday,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.holidayValidator.validate(holiday, result);
		
		if (result.hasErrors()) {
			return "moderator/holiday/addNew";
		}
		
		try {
			TransactionResult tr = this.holidayService.addHoliday(holiday, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/moderator/holiday/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/moderator/holiday/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/moderator/holiday/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/holiday/list/current";
		}
	}
}
