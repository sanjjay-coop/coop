package org.pf.coop.portal.controller.manager.messageText;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.service.MessageTextService;
import org.pf.coop.portal.validators.edit.MessageTextEditValidator;
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
@RequestMapping("/manager/messageText/edit")
public class MessageTextEditController extends ManagerBaseController {

	@Autowired
	private MessageTextService messageTextService;
	
	@Autowired
	private MessageTextEditValidator messageTextEditValidator;
		
	@GetMapping("/{id}")
	public String editMessageText(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			MessageText messageText = (MessageText) this.messageTextService.getById(id);
			
			if (messageText == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/messageText/addNew";
			}
	
			model.addAttribute("messageText", messageText);
			
			return "manager/messageText/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/messageText/addNew";
		}
	}

	@PostMapping("/*")
	public String editMessageText(@ModelAttribute MessageText messageText,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.messageTextEditValidator.validate(messageText, result);
		
		if (result.hasErrors()) {
			return "manager/messageText/edit";
		}
		
		try {
			TransactionResult tr = this.messageTextService.updateMessageText(messageText, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/messageText/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/messageText/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/messageText/edit/"+messageText.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/messageText/edit/"+messageText.getId();
		}
	}
}
