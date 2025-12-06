package org.pf.coop.portal.controller.mobile.home.title;

import java.security.Principal;
import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.library.TitleRepo;
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
@RequestMapping("/mobile/home/title")
public class MobileHomeTitleListController extends MobileBaseController {

	@Autowired
	private EntityManager entityManager;
	
	private String errorMessage;
	private int resultSize = 10;
	private long pageNumber = 0;
	private long totalRecords = 0;
	private long totalPages = 0;
	private List<Title> listTitle;
	
	@Autowired
	private TitleRepo titleRepo;
	
	private Boolean validObject(Title obj) {
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
	public String listTitle(@ModelAttribute Title title, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			if (!this.validObject(title)) reat.addFlashAttribute("message", this.errorMessage); 
				
			request.getSession().setAttribute("mobileSearch_title", title);
				
			return "redirect:/mobile/home/title/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/mobile/index";
		}
	}
	
	@GetMapping("/list")
	public String listTitle(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		this.pageNumber = 0;
		
		Title obj = (Title) request.getSession().getAttribute("mobileSearch_title");

		if (obj == null) { obj = new Title();} // obj was null
		
		this.searchTitle(obj);
		
		model.addAttribute("currentPage", this.pageNumber + 1);
		model.addAttribute("totalPages", this.totalPages);

		model.addAttribute("totalRecords", this.totalRecords);
		
		request.getSession().setAttribute("mobileSearch_title", obj);
		model.addAttribute("title", obj);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listMobileTitle_pageNumber", this.pageNumber);
		request.getSession().setAttribute("listMobileTitle_totalPages", this.totalPages);
		
		model.addAttribute("listTitle", this.listTitle);
		
		return "mobile/home/title/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listTitle(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			this.pageNumber = (int) request.getSession().getAttribute("listMobileTitle_pageNumber");
			this.totalPages = (int) request.getSession().getAttribute("listMobileTitle_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/title/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Title obj = (Title) request.getSession().getAttribute("mobileSearch_title");
			
			if (obj == null) { obj = new Title(); }
			
			this.searchTitle(obj);
			
			model.addAttribute("currentPage", this.pageNumber + 1);
			model.addAttribute("totalPages", this.totalPages);

			model.addAttribute("totalRecords", this.totalRecords);
			
			request.getSession().setAttribute("mobileSearch_title", obj);
			model.addAttribute("title", obj);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listMobileTitle_pageNumber", this.pageNumber);
			request.getSession().setAttribute("listMobileTitle_totalPages", this.totalPages);
			
			model.addAttribute("listTitle", this.listTitle);
			
			return "mobile/home/title/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/title/list";
		}
	}
	
	private void searchTitle(Title obj) {
		
		if (this.validObject(obj)) {
			
			final String str = obj.getSearchFor();
			
			SearchSession searchSession = Search.session(this.entityManager);
			
			SearchResult<Title> result = searchSession.search(Title.class)
					.where(f -> f.bool()
							.must(f.match().field("searchString").matching(str))
							)
					.fetch((int) this.pageNumber, this.resultSize);
			
			this.totalRecords = result.total().hitCount();
			this.totalPages = this.getTotalPages(totalRecords);
			
			this.listTitle = result.hits();
			
		} else {
			
			Pageable pageable = PageRequest.of((int) this.pageNumber, this.resultSize, Sort.by(Sort.Direction.ASC, "uniformTitle"));
			
			Page<Title> page = this.titleRepo.findAll( 
					pageable);
			
			totalRecords = page.getTotalElements();
			totalPages = page.getTotalPages();
			
			this.listTitle = page.getContent();
		}
	}
}
