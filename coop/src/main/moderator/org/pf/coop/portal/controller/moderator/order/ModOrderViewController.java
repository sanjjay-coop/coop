package org.pf.coop.portal.controller.moderator.order;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Order;
import org.pf.coop.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moderator/order/view")
public class ModOrderViewController extends ModeratorBaseController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/{id}")
	public String editOrder(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Order order = (Order) this.orderService.getById(id);
			
			if (order == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/moderator/order/list/current";
			}
	
			model.addAttribute("order", order);
			
			return "moderator/order/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/moderator/order/list/current";
		}
	}
}
