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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/menuItem/edit")
public class MenuItemEditController extends ManagerBaseController {

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
	
	@GetMapping("/{id}")
	public String editMenuItem(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			MenuItem menuItem = (MenuItem) this.menuItemService.getById(id);
			
			if (menuItem == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/menuItem/addNew";
			}
	
			model.addAttribute("menuItem", menuItem);
			
			return "manager/menuItem/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/menuItem/addNew";
		}
	}

	@PostMapping("/*")
	public String editMenuItem(@ModelAttribute MenuItem menuItem,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.menuItemValidator.validate(menuItem, result);
		
		if (result.hasErrors()) {
			return "manager/menuItem/edit";
		}
		
		try {
			TransactionResult tr = this.menuItemService.updateMenuItem(menuItem, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/menuItem/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/menuItem/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/menuItem/edit/"+menuItem.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/menuItem/edit/"+menuItem.getId();
		}
	}
}
