package org.pf.coop.portal.controller.article;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.controller.IndexBaseController;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.pf.coop.portal.service.MenuItemService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/category/blog")
public class ArticleBlogController extends IndexBaseController {

	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private EventRepo eventRepo;
	
	@Autowired
	private MenuItemService menuItemService;
	
	@ModelAttribute("listEvent")
	public List<Event> getListEvent(){
		return (List<Event>) this.eventRepo.listEventRecent((Calendar.getInstance()).getTime());
	}
	
	@GetMapping("/{catId}")
	public String listArticle(@PathVariable Long catId, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		MenuItem menuItem = (MenuItem) this.menuItemService.getById(catId);
		
		if (menuItem == null) {
			reat.addFlashAttribute("message", "No such Menu Item");
			return "redirect:/";
		}
		
		Category category = (Category) menuItem.getCategory();
		
		if (category==null) {
			reat.addFlashAttribute("message", "No such category found.");
			return "redirect:/";
		}
		
		model.addAttribute("category", category);
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "pubDate"));
		
		Page<Article> page = this.articleRepo.listArticleBlog(Calendar.getInstance().getTime(), category.getName(), pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listArticle", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listBlogArticle_pageNumber", pageNumber);
		request.getSession().setAttribute("listBlogArticle_totalPages", totalPages);
		
		return "article/blog";
	}
	
	@GetMapping("/{catId}/{whichPage}")
	public String listArticle(@PathVariable Long catId, @PathVariable String whichPage, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		MenuItem menuItem = (MenuItem) this.menuItemService.getById(catId);
		
		if (menuItem == null) {
			reat.addFlashAttribute("message", "No such Menu Item");
			return "redirect:/";
		}
		
		Category category = (Category) menuItem.getCategory();
		
		if (category==null) {
			reat.addFlashAttribute("message", "No such category found.");
			return "redirect:/";
		}
		
		model.addAttribute("category", category);
				
		try {
			int pageNumber = (int) request.getSession().getAttribute("listBlogArticle_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listBlogArticle_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/article/blog/" + category.getId();
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "pubDate"));
			
			Page<Article> page = this.articleRepo.listArticleBlog(Calendar.getInstance().getTime(), category.getName(), pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listBlogArticle_pageNumber", pageNumber);
			request.getSession().setAttribute("listBlogArticle_totalPages", totalPages);
			
			model.addAttribute("listArticle", page.getContent());
			
			return "article/blog";
		
		} catch(Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "top";
	}
}
