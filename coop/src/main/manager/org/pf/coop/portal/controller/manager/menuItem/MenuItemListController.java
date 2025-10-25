package org.pf.coop.portal.controller.manager.menuItem;

import java.security.Principal;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.repository.MenuItemRepo;
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
@RequestMapping("/manager/menuItem")
public class MenuItemListController extends ManagerBaseController {
	
	@Autowired
	private MenuItemRepo menuItemRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listMenuItem(@ModelAttribute MenuItem menuItem, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("managerSearch_menuItem", menuItem);
				
			return "redirect:/manager/menuItem/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listMenuItem(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<MenuItem> page;
			
			MenuItem obj = (MenuItem) request.getSession().getAttribute("managerSearch_menuItem");
			
			if (obj == null) {
				page = this.menuItemRepo.findAll(pageable);
				obj = new MenuItem();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.menuItemRepo.findAll(pageable);
				} else {
					page = this.menuItemRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("managerSearch_menuItem", obj);
			model.addAttribute("menuItem", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listMenuItem", page.getContent());
			
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
			
			request.getSession().setAttribute("listMenuItemManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listMenuItemManager_totalPages", totalPages);
			
			return "manager/menuItem/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listMenuItem(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listMenuItemManager_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listMenuItemManager_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/menuItem/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<MenuItem> page;
			
			MenuItem obj = (MenuItem) request.getSession().getAttribute("managerSearch_menuItem");
			
			if (obj == null) {
				page = this.menuItemRepo.findAll(pageable);
				obj = new MenuItem();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.menuItemRepo.findAll(pageable);
				} else {
					page = this.menuItemRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("managerSearch_menuItem", obj);
			model.addAttribute("menuItem", obj);
			
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
			
			request.getSession().setAttribute("listMenuItemManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listMenuItemManager_totalPages", totalPages);
			
			model.addAttribute("listMenuItem", page.getContent());
			
			return "manager/menuItem/list";
		
		} catch(Exception e) {
			return "redirect:/manager/menuItem/list";
		}
	}
}

