package org.pf.coop.portal.controller.manager.orderCategory;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.service.OrderCategoryService;
import org.pf.coop.portal.validators.add.OrderCategoryAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/orderCategory/addNew")
public class OrderCategoryAddController extends ManagerBaseController {
	
	@Autowired
	private OrderCategoryService orderCategoryService;
	
	@Autowired
	private OrderCategoryAddValidator orderCategoryAddValidator;
	
	@GetMapping
	public String orderCategoryAdd(Model model) {
		
		OrderCategory orderCategory = new OrderCategory();
		
		model.addAttribute(orderCategory);
		
		return "manager/orderCategory/addNew";
	}
	
	@PostMapping
	public String orderCategoryAdd(@ModelAttribute OrderCategory orderCategory,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.orderCategoryAddValidator.validate(orderCategory, result);
		
		if (result.hasErrors()) {
			return "manager/orderCategory/addNew";
		}
		
		try {
			TransactionResult tr = this.orderCategoryService.addOrderCategory(orderCategory, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/orderCategory/list/current";
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/orderCategory/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/orderCategory/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/orderCategory/list/current";
		}
	}
}
