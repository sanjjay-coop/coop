package org.pf.coop.portal.controller.manager.eventType;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.repository.EventTypeRepo;
import org.pf.coop.portal.service.EventTypeService;
import org.pf.coop.portal.validators.add.EventTypeAddValidator;
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
@RequestMapping(value = "/manager/eventType/addNew")
public class EventTypeAddController extends ManagerBaseController {

	@Autowired
	private EventTypeRepo eventTypeRepo;
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private EventTypeAddValidator eventTypeAddValidator;
	
	@ModelAttribute("listEventType")
	public List<EventType> getListEventType(){
		return (List<EventType>) this.eventTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String eventTypeAdd(Model model) {
		
		EventType eventType = new EventType();
		
		model.addAttribute(eventType);
		
		return "manager/eventType/addNew";
	}
	
	@PostMapping
	public String eventTypeAdd(@ModelAttribute EventType eventType,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.eventTypeAddValidator.validate(eventType, result);
		
		if (result.hasErrors()) {
			return "manager/eventType/addNew";
		}
		
		try {
			TransactionResult tr = this.eventTypeService.addEventType(eventType, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/eventType/addNew";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/eventType/addNew";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/eventType/addNew";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/eventType/addNew";
		}
	}
}
