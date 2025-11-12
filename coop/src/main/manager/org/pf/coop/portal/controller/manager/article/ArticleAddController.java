package org.pf.coop.portal.controller.manager.article;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.repository.CategoryRepo;
import org.pf.coop.portal.service.ArticleService;
import org.pf.coop.portal.validators.ArticleValidator;
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
@RequestMapping(value = "/manager/article/addNew")
public class ArticleAddController extends ManagerBaseController {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleValidator articleValidator;
	
	@ModelAttribute("listCategory")
	public List<Category> getListCategory(){
		return (List<Category>) this.categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String articleAdd(Model model) {
		
		Article article = new Article();
		
		model.addAttribute(article);
		
		return "manager/article/addNew";
	}
	
	@PostMapping
	public String articleAdd(@ModelAttribute Article article,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.articleValidator.validate(article, result);
		
		if (result.hasErrors()) {
			return "manager/article/addNew";
		}
		
		try {
			TransactionResult tr = this.articleService.addArticle(article, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/manager/article/list/current";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/article/list";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/manager/article/list/current";				
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/article/list/current";
		}
	}
}
