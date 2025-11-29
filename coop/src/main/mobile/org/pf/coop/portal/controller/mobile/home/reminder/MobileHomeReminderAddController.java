package org.pf.coop.portal.controller.mobile.home.reminder;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Reminder;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.ReminderService;
import org.pf.coop.portal.validators.ReminderValidator;
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
@RequestMapping(value = "/mobile/home/reminder/addNew")
public class MobileHomeReminderAddController extends MobileBaseController {

	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private ReminderValidator reminderValidator;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping
	public String reminderAdd(Model model) {
		
		Reminder reminder = new Reminder();
		
		model.addAttribute(reminder);
		
		return "mobile/home/reminder/addNew";
	}
	
	@PostMapping
	public String reminderAdd(@ModelAttribute Reminder reminder,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.reminderValidator.validate(reminder, result);
		
		if (result.hasErrors()) {
			return "mobile/home/reminder/addNew";
		}
		
		try {
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			reminder.setMember(member);
			reminder.setRemBySelf(true);
			
			TransactionResult tr = this.reminderService.addReminder(reminder, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/mobile/home/reminder/list/current";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/mobile/home/reminder/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/mobile/home/reminder/list/current";				
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/mobile/home/reminder/list/current";
		}
	}
}