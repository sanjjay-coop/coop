package org.pf.coop.portal.controller.manager.eventType;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.service.EventTypeService;
import org.pf.coop.portal.validators.edit.EventTypeEditValidator;
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
@RequestMapping("/manager/eventType/edit")
public class EventTypeEditController extends ManagerBaseController {

	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private EventTypeEditValidator eventTypeEditValidator;
		
	@GetMapping("/{id}")
	public String editEventType(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			EventType eventType = (EventType) this.eventTypeService.getById(id);
			
			if (eventType == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/eventType/addNew";
			}
	
			model.addAttribute("eventType", eventType);
			
			return "manager/eventType/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/eventType/addNew";
		}
	}

	@PostMapping("/*")
	public String editEventType(@ModelAttribute EventType eventType,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.eventTypeEditValidator.validate(eventType, result);
		
		if (result.hasErrors()) {
			return "manager/eventType/edit";
		}
		
		try {
			TransactionResult tr = this.eventTypeService.updateEventType(eventType, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/eventType/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/eventType/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/eventType/edit/"+eventType.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/eventType/edit/"+eventType.getId();
		}
	}
}
