package org.pf.coop.portal.controller.article;

import java.security.Principal;
import java.util.Calendar;

import org.pf.coop.portal.controller.BaseController;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/article")
public class GlobalArticleListController extends BaseController  {

	@Autowired
	private ArticleRepo articleRepo;
	
	@GetMapping("/list")
	public String listArticle(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 24, Sort.by(Sort.Direction.DESC, "pubDate"));
		
		Page<Article> page = this.articleRepo.findByPublishAndPubDateLessThanEqualAndExpDateGreaterThanEqual(
				true,
				(Calendar.getInstance()).getTime(),
				(Calendar.getInstance()).getTime(), 
				pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listArticle", page.getContent());
		
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
		
		request.getSession().setAttribute("listGlobalArticle_pageNumber", pageNumber);
		request.getSession().setAttribute("listGlobalArticle_totalPages", totalPages);
		
		return "article/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listArticle(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listGlobalArticle_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listGlobalArticle_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/article/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 24, Sort.by(Sort.Direction.DESC, "pubDate"));
			
			Page<Article> page = this.articleRepo.findByPublishAndPubDateLessThanEqualAndExpDateGreaterThanEqual(
					true,
					(Calendar.getInstance()).getTime(),
					(Calendar.getInstance()).getTime(), 
					pageable);
			
			totalPages = page.getTotalPages();
			
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
			
			request.getSession().setAttribute("listGlobalArticle_pageNumber", pageNumber);
			request.getSession().setAttribute("listGlobalArticle_totalPages", totalPages);
			
			model.addAttribute("listArticle", page.getContent());
			
			return "article/list";
		
		} catch(Exception e) {
			return "redirect:/article/list";
		}
	}
}
