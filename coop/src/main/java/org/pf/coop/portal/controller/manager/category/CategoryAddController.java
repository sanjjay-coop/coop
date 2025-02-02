package org.pf.coop.portal.controller.manager.category;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.repository.CategoryRepo;
import org.pf.coop.portal.service.CategoryService;
import org.pf.coop.portal.validators.add.CategoryAddValidator;
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
@RequestMapping(value = "/manager/category/addNew")
public class CategoryAddController extends ManagerBaseController {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryAddValidator categoryAddValidator;
	
	@ModelAttribute("listCategory")
	public List<Category> getListCategory(){
		return (List<Category>) this.categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String categoryAdd(Model model) {
		
		Category category = new Category();
		
		model.addAttribute(category);
		
		return "manager/category/addNew";
	}
	
	@PostMapping
	public String categoryAdd(@ModelAttribute Category category,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.categoryAddValidator.validate(category, result);
		
		if (result.hasErrors()) {
			return "manager/category/addNew";
		}
		
		try {
			TransactionResult tr = this.categoryService.addCategory(category, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/category/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/category/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/category/addNew";
		}
	}
}
