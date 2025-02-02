package org.pf.coop.portal.controller.manager.event.update;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.model.EventUpdate;
import org.pf.coop.portal.service.EventService;
import org.pf.coop.portal.service.EventUpdateService;
import org.pf.coop.portal.validators.EventUpdateValidator;
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
@RequestMapping(value = "/manager/eventUpdate/addNew")
public class EventUpdateAddController extends ManagerBaseController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventUpdateService eventUpdateService;
	
	@Autowired
	private EventUpdateValidator eventUpdateValidator;
	
	@GetMapping("/{id}")
	public String eventUpdateAdd(@PathVariable Long id, Model model,
			RedirectAttributes reat) {
		
		try {
			EventUpdate eventUpdate = new EventUpdate();
			
			Event event = (Event) this.eventService.getById(id);
			
			if (event == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/event/list/current";
			}
			
			eventUpdate.setEvent(event);
			
			model.addAttribute(eventUpdate);
			
			return "manager/eventUpdate/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/event/list/current";
		}
	}
	
	@PostMapping("/*")
	public String eventUpdateAdd(@ModelAttribute EventUpdate eventUpdate,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.eventUpdateValidator.validate(eventUpdate, result);
		
		if (result.hasErrors()) {
			return "manager/eventUpdate/addNew";
		}
		
		try {
			TransactionResult tr = this.eventUpdateService.addEventUpdate(eventUpdate, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/event/view/" + eventUpdate.getEvent().getId();
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/event/view/" + eventUpdate.getEvent().getId();
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/event/view/" + eventUpdate.getEvent().getId();
		}
	}
}
