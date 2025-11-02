package org.pf.coop.portal.controller.moderator.order;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Order;
import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.repository.OrderCategoryRepo;
import org.pf.coop.portal.service.OrderService;
import org.pf.coop.portal.validators.OrderValidator;
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
@RequestMapping("/moderator/order/edit")
public class ModOrderEditController extends ModeratorBaseController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderValidator orderValidator;

	@Autowired
	OrderCategoryRepo orderCategoryRepo;
	
	@ModelAttribute("listOrderCategory")
	public List<OrderCategory> getListOrderCategory(){
		return (List<OrderCategory>) this.orderCategoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/{id}")
	public String editOrder(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Order order = (Order) this.orderService.getById(id);
			
			if (order == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/order/addNew";
			}
	
			model.addAttribute("order", order);
			
			return "moderator/order/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/order/addNew";
		}
	}

	@PostMapping("/*")
	public String editOrder(@ModelAttribute Order order,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.orderValidator.validate(order, result);
		
		if (result.hasErrors()) {
			return "moderator/order/edit";
		}
		
		try {
			TransactionResult tr = this.orderService.updateOrder(order, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/moderator/order/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/moderator/order/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/moderator/order/list/current";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/moderator/order/list/current";
		}
	}
}
