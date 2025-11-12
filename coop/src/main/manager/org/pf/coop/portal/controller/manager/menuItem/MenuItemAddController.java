package org.pf.coop.portal.controller.manager.menuItem;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.repository.CategoryRepo;
import org.pf.coop.portal.service.MenuItemService;
import org.pf.coop.portal.validators.MenuItemValidator;
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
@RequestMapping(value = "/manager/menuItem/addNew")
public class MenuItemAddController extends ManagerBaseController {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private MenuItemService menuItemService;
	
	@Autowired
	private MenuItemValidator menuItemValidator;
	
	@ModelAttribute("listCategory")
	public List<Category> getListCategory(){
		return (List<Category>) this.categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String menuItemAdd(Model model) {
		
		MenuItem menuItem = new MenuItem();
		
		model.addAttribute(menuItem);
		
		return "manager/menuItem/addNew";
	}
	
	@PostMapping
	public String menuItemAdd(@ModelAttribute MenuItem menuItem,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.menuItemValidator.validate(menuItem, result);
		
		if (result.hasErrors()) {
			return "manager/menuItem/addNew";
		}
		
		try {
			TransactionResult tr = this.menuItemService.addMenuItem(menuItem, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/menuItem/list/current";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/menuItem/list";
			} else {
				reat.addFlashAttribute("message", "Error: " + tr.getMessage());
				return "redirect:/manager/menuItem/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/menuItem/list/current";
		}
	}
}
