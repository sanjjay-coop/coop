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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/event/edit")
public class EventEditController extends ManagerBaseController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventValidator eventValidator;
	
	@Autowired
	private EventTypeRepo eventTypeRepo;
	
	@ModelAttribute("listEventType")
	public List<EventType> getListEventType(){
		return (List<EventType>) this.eventTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
		
	@GetMapping("/{id}")
	public String editEvent(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Event event = (Event) this.eventService.getById(id);
			
			if (event == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/event/addNew";
			}
	
			model.addAttribute("event", event);
			
			return "manager/event/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/event/addNew";
		}
	}

	@PostMapping("/*")
	public String editEvent(@ModelAttribute Event event,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.eventValidator.validate(event, result);
		
		if (result.hasErrors()) {
			return "manager/event/edit";
		}
		
		try {
			TransactionResult tr = this.eventService.updateEvent(event, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/event/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/event/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/event/edit/"+event.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/event/edit/"+event.getId();
		}
	}
}
