package org.pf.coop.portal.controller.home.order;

import java.security.Principal;

import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Order;
import org.pf.coop.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeOrderFileController extends HomeBaseController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/home/order/file/{id}")
	public ResponseEntity<byte[]> orderFileDownload(@PathVariable long id, Model model, RedirectAttributes reat, Principal principal) {
		
		Order obj = (Order) this.orderService.getById(id);
			
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + obj.getFileName() + "\"")
		        .body(obj.getDocument());
	}
}
