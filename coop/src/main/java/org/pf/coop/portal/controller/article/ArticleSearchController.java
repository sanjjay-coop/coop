package org.pf.coop.portal.controller.article;

import java.security.Principal;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/article/search")
public class ArticleSearchController extends BaseController {
	
	@Autowired
	private EntityManager entityManager;
	
	private String errorMessage;
	private int resultSize = 10;
	
	private Boolean validObject(Article obj) {
		if (obj == null) {			
			this.errorMessage = "Search string is empty.";
			return false;
		} else {
			if (obj.getSearchFor()==null) {
				this.errorMessage = "Search string is empty.";
				return false;
			} else {
				if (obj.getSearchFor().isBlank() || obj.getSearchFor().length()<3) {
					this.errorMessage = "Search string must be of 3 or more charaters.";
					return false;
				}
			}
		}
		return true;
	}
	
	private long getTotalPages(long totalRecords) {
		long quotient = totalRecords / this.resultSize;
		long remainder = totalRecords % this.resultSize;
		if (remainder == 0) return quotient;
		else return quotient + 1;
	}
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listBusiness(@ModelAttribute Article article, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			if (!this.validObject(article)) {
				reat.addFlashAttribute("message", this.errorMessage);
				return "redirect:/article/list/current";
			} 
			
			request.getSession().setAttribute("globalSearchAll_article", article);
				
			return "redirect:/article/search/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listArticle(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		long pageNumber = 0;
		
		Article obj = (Article) request.getSession().getAttribute("globalSearchAll_article");
		
		if (!this.validObject(obj)) {
			reat.addFlashAttribute("message", this.errorMessage);
			return "redirect:/article/list/current";
		} 
		
		request.getSession().setAttribute("globalSearchAll_article", obj);
		model.addAttribute("article", obj);
		
		final String str = obj.getSearchFor();
		
		SearchSession searchSession = Search.session(entityManager);
		
		SearchResult<Article> result = searchSession.search(Article.class)
				.where(f -> f.match()
						.field("content")
						.matching(str))
				.fetch(0, resultSize);
		
		long totalRecords = result.total().hitCount();
		long totalPages = this.getTotalPages(totalRecords);
		
		System.out.println("Total pages: " + totalPages + " -- Total records: " + totalRecords);
		
		model.addAttribute("listArticle", result.hits());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("totalRecords", totalRecords);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listGlobalArticle_pageNumber", pageNumber);
		request.getSession().setAttribute("listGlobalArticle_totalPages", totalPages);
		
		return "article/search";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listArticle(@PathVariable String whichPage, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			long pageNumber = (long) request.getSession().getAttribute("listGlobalArticle_pageNumber");
			long totalPages = (long) request.getSession().getAttribute("listGlobalArticle_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/article/search/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Article obj = (Article) request.getSession().getAttribute("globalSearchAll_article");
			
			if (!this.validObject(obj)) {
				reat.addFlashAttribute("message", this.errorMessage);
				return "redirect:/article/list/current";
			} 
			
			request.getSession().setAttribute("globalSearchAll_article", obj);
			model.addAttribute("article", obj);
			
			final String str = obj.getSearchFor();
			
			SearchSession searchSession = Search.session(entityManager);
			
			SearchResult<Article> result = searchSession.search(Article.class)
					.where(f -> f.match()
							.field("content")
							.matching(str))
					.fetch((int) pageNumber*resultSize, resultSize);
			
			long totalRecords = result.total().hitCount();
			totalPages = this.getTotalPages(totalRecords);
			
			model.addAttribute("listArticle", result.hits());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);

			model.addAttribute("totalRecords", totalRecords);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listGlobalArticle_pageNumber", pageNumber);
			request.getSession().setAttribute("listGlobalArticle_totalPages", totalPages);
			
			return "article/search";
		
		} catch(Exception e) {
			return "redirect:/article/search/list";
		}
	}
}
