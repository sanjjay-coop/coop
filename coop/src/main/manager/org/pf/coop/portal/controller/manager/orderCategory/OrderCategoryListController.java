package org.pf.coop.portal.controller.manager.orderCategory;

import java.security.Principal;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.repository.OrderCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager/orderCategory")
public class OrderCategoryListController extends ManagerBaseController {
	
	@Autowired
	private OrderCategoryRepo orderCategoryRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listOrderCategory(@ModelAttribute OrderCategory orderCategory, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("managerSearch_orderCategory", orderCategory);
				
			return "redirect:/manager/orderCategory/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listOrderCategory(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
			
			Page<OrderCategory> page;
			
			OrderCategory obj = (OrderCategory) request.getSession().getAttribute("managerSearch_orderCategory");
			
			if (obj == null) {
				page = this.orderCategoryRepo.findAll(pageable);
				obj = new OrderCategory();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.orderCategoryRepo.findAll(pageable);
				} else {
					page = this.orderCategoryRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("managerSearch_orderCategory", obj);
			model.addAttribute("orderCategory", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listOrderCategory", page.getContent());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listOrderCategoryManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listOrderCategoryManager_totalPages", totalPages);
			
			return "manager/orderCategory/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listOrderCategory(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listOrderCategoryManager_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listOrderCategoryManager_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/orderCategory/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
			
			Page<OrderCategory> page;
			
			OrderCategory obj = (OrderCategory) request.getSession().getAttribute("managerSearch_orderCategory");
			
			if (obj == null) {
				page = this.orderCategoryRepo.findAll(pageable);
				obj = new OrderCategory();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.orderCategoryRepo.findAll(pageable);
				} else {
					page = this.orderCategoryRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("managerSearch_orderCategory", obj);
			model.addAttribute("orderCategory", obj);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listOrderCategoryManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listOrderCategoryManager_totalPages", totalPages);
			
			model.addAttribute("listOrderCategory", page.getContent());
			
			return "manager/orderCategory/list";
		
		} catch(Exception e) {
			return "redirect:/manager/orderCategory/list";
		}
	}
}
