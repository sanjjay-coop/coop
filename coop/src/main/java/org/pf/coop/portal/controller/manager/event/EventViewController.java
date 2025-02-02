package org.pf.coop.portal.controller.manager.event;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/event/view")
public class EventViewController extends ManagerBaseController {
	
	@Autowired
	private EventService eventService;
		
	@GetMapping("/{id}")
	public String viewEvent(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Event event = (Event) this.eventService.getById(id);
			
			if (event == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/event/addNew";
			}
	
			model.addAttribute("event", event);
			
			return "manager/event/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/event/list/current";
		}
	}
}
