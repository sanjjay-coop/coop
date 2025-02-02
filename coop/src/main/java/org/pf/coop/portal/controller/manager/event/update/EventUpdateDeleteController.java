package org.pf.coop.portal.controller.manager.event.update;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.EventUpdate;
import org.pf.coop.portal.service.EventUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/eventUpdate/delete")
public class EventUpdateDeleteController extends ManagerBaseController {

	@Autowired
	private EventUpdateService eventUpdateService;

	@GetMapping("/{id}")
	public String deleteEventUpdate(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			EventUpdate eventUpdate = (EventUpdate) this.eventUpdateService.getById(id);
			
			if (eventUpdate == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/event/list/current";
			}
			
			Long eventId = eventUpdate.getEvent().getId();
			
			TransactionResult tr = this.eventUpdateService.deleteEventUpdate(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/manager/event/view/" + eventId;
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/manager/event/view/" + eventId;
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/manager/event/view/" + eventId;
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/manager/event/list/current";
			
		}
	}
}
