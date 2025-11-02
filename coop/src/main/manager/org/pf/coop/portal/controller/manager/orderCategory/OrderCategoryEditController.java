package org.pf.coop.portal.controller.manager.orderCategory;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.service.OrderCategoryService;
import org.pf.coop.portal.validators.edit.OrderCategoryEditValidator;
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
@RequestMapping("/manager/orderCategory/edit")
public class OrderCategoryEditController extends ManagerBaseController {

	@Autowired
	private OrderCategoryService orderCategoryService;
	
	@Autowired
	private OrderCategoryEditValidator orderCategoryEditValidator;
		
	@GetMapping("/{id}")
	public String editOrderCategory(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			OrderCategory orderCategory = (OrderCategory) this.orderCategoryService.getById(id);
			
			if (orderCategory == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/orderCategory/list/current";
			}
	
			model.addAttribute("orderCategory", orderCategory);
			
			return "manager/orderCategory/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/orderCategory/list/current";
		}
	}

	@PostMapping("/*")
	public String editOrderCategory(@ModelAttribute OrderCategory orderCategory,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.orderCategoryEditValidator.validate(orderCategory, result);
		
		if (result.hasErrors()) {
			return "manager/orderCategory/edit";
		}
		
		try {
			TransactionResult tr = this.orderCategoryService.updateOrderCategory(orderCategory, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/orderCategory/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/orderCategory/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/orderCategory/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/orderCategory/list/current";
		}
	}
}


