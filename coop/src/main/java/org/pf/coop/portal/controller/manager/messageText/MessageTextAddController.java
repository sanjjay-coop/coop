package org.pf.coop.portal.controller.manager.messageText;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.service.MessageTextService;
import org.pf.coop.portal.validators.add.MessageTextAddValidator;
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
@RequestMapping(value = "/manager/messageText/addNew")
public class MessageTextAddController extends ManagerBaseController {

	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private MessageTextService messageTextService;
	
	@Autowired
	private MessageTextAddValidator messageTextAddValidator;
	
	@ModelAttribute("listMessageText")
	public List<MessageText> getListMessageText(){
		return (List<MessageText>) this.messageTextRepo.findAll(Sort.by(Sort.Direction.ASC, "messageFor"));
	}
	
	@GetMapping
	public String messageTextAdd(Model model) {
		
		MessageText messageText = new MessageText();
		
		model.addAttribute(messageText);
		
		return "manager/messageText/addNew";
	}
	
	@PostMapping
	public String messageTextAdd(@ModelAttribute MessageText messageText,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.messageTextAddValidator.validate(messageText, result);
		
		if (result.hasErrors()) {
			return "manager/messageText/addNew";
		}
		
		try {
			TransactionResult tr = this.messageTextService.addMessageText(messageText, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/messageText/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/messageText/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/messageText/addNew";
		}
	}
}
