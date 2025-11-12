package org.pf.coop.portal.controller.manager.event;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.repository.EventTypeRepo;
import org.pf.coop.portal.service.EventService;
import org.pf.coop.portal.validators.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/event/addNew")
public class EventAddController extends ManagerBaseController {

	@Autowired
	private EventTypeRepo eventTypeRepo;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventValidator eventValidator;
	
	@ModelAttribute("listEventType")
	public List<EventType> getListEventType(){
		return (List<EventType>) this.eventTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String eventAdd(Model model) {
		
		Event event = new Event();
		
		model.addAttribute(event);
		
		return "manager/event/addNew";
	}
	
	@PostMapping
	public String eventAdd(@ModelAttribute Event event,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.eventValidator.validate(event, result);
		
		if (result.hasErrors()) {
			return "manager/event/addNew";
		}
		
		try {
			TransactionResult tr = this.eventService.addEvent(event, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/event/addNew";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/event/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/event/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/event/addNew";
		}
	}
}
