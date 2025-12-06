package org.pf.coop.portal.controller.mobile.article;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.repository.ArticleRepo;
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

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mobile/article")
public class MobileArticleListController extends MobileBaseController {

	@Autowired
	private EntityManager entityManager;
	
	private String errorMessage;
	private int resultSize = 10;
	private long pageNumber = 0;
	private long totalRecords = 0;
	private long totalPages = 0;
	private List<Article> listArticle;
	
	@Autowired
	private ArticleRepo articleRepo;
	
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
	public String listArticle(@ModelAttribute Article article, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			if (!this.validObject(article)) reat.addFlashAttribute("message", this.errorMessage); 
				
			request.getSession().setAttribute("mobileSearch_article", article);
				
			return "redirect:/mobile/article/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/index";
		}
	}
	
	@GetMapping("/list")
	public String listArticle(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		this.pageNumber = 0;
		
		Article obj = (Article) request.getSession().getAttribute("mobileSearch_article");

		if (obj == null) { obj = new Article();} // obj was null
		
		this.searchArticle(obj);
		
		model.addAttribute("currentPage", this.pageNumber + 1);
		model.addAttribute("totalPages", this.totalPages);

		model.addAttribute("totalRecords", this.totalRecords);
		
		request.getSession().setAttribute("mobileSearch_article", obj);
		model.addAttribute("article", obj);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listMobileArticle_pageNumber", this.pageNumber);
		request.getSession().setAttribute("listMobileArticle_totalPages", this.totalPages);
		
		model.addAttribute("listArticle", this.listArticle);
		
		return "mobile/article/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listArticle(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			this.pageNumber = (int) request.getSession().getAttribute("listMobileArticle_pageNumber");
			this.totalPages = (int) request.getSession().getAttribute("listMobileArticle_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/article/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Article obj = (Article) request.getSession().getAttribute("mobileSearch_article");
			
			if (obj == null) { obj = new Article(); }
			
			this.searchArticle(obj);
			
			model.addAttribute("currentPage", this.pageNumber + 1);
			model.addAttribute("totalPages", this.totalPages);

			model.addAttribute("totalRecords", this.totalRecords);
			
			request.getSession().setAttribute("mobileSearch_article", obj);
			model.addAttribute("article", obj);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listMobileArticle_pageNumber", this.pageNumber);
			request.getSession().setAttribute("listMobileArticle_totalPages", this.totalPages);
			
			model.addAttribute("listArticle", this.listArticle);
			
			return "mobile/article/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/article/list";
		}
	}
	
	private void searchArticle(Article obj) {
		
		if (this.validObject(obj)) {
			
			final String str = obj.getSearchFor();
			
			SearchSession searchSession = Search.session(this.entityManager);
			
			SearchResult<Article> result = searchSession.search(Article.class)
					.where(f -> f.bool()
							.must(f.match().field("searchString").matching(str))
							.must(f.match().field("publish").matching(true))
							.must(f.range().field("pubDate").atMost(Calendar.getInstance().getTime()))
							.must(f.range().field("expDate").greaterThan(Calendar.getInstance().getTime()))
							)
					.fetch((int) this.pageNumber, this.resultSize);
			
			this.totalRecords = result.total().hitCount();
			this.totalPages = this.getTotalPages(totalRecords);
			
			this.listArticle = result.hits();
			
		} else {
			
			Pageable pageable = PageRequest.of((int) this.pageNumber, this.resultSize, Sort.by(Sort.Direction.DESC, "pubDate"));
			
			Page<Article> page = this.articleRepo.findByPublishAndPubDateLessThanEqualAndExpDateGreaterThanEqual(
					true,
					(Calendar.getInstance()).getTime(),
					(Calendar.getInstance()).getTime(), 
					pageable);
			
			totalRecords = page.getTotalElements();
			totalPages = page.getTotalPages();
			
			this.listArticle = page.getContent();
		}
	}
}
