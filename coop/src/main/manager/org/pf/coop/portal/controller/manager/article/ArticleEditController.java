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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/article/edit")
public class ArticleEditController extends ManagerBaseController {

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
	
	@GetMapping("/{id}")
	public String editArticle(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Article article = (Article) this.articleService.getById(id);
			
			if (article == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/article/addNew";
			}
	
			model.addAttribute("article", article);
			
			return "manager/article/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/article/addNew";
		}
	}

	@PostMapping("/*")
	public String editArticle(@ModelAttribute Article article,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.articleValidator.validate(article, result);
		
		if (result.hasErrors()) {
			return "manager/article/edit";
		}
		
		try {
			TransactionResult tr = this.articleService.updateArticle(article, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/article/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/article/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/article/edit/"+article.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/article/edit/"+article.getId();
		}
	}
}
