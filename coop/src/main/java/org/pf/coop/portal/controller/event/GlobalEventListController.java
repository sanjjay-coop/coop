package org.pf.coop.portal.controller.event;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.EventRepo;
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

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/event")
public class GlobalEventListController extends BaseController  {

	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private EventRepo eventRepo;
	
	@ModelAttribute("listArticle")
	public List<Article> getListArticle(){
		return (List<Article>) this.articleRepo.listArticleRecent((Calendar.getInstance()).getTime());
	}
	
	@ModelAttribute("listEvent")
	public List<Event> getListEvent(){
		return (List<Event>) this.eventRepo.listEventRecent((Calendar.getInstance()).getTime());
	}
	
	@GetMapping("/list")
	public String listEvent(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "startDate"));
		
		Page<Event> page = this.eventRepo.findByPublishAndEndDateGreaterThanEqual(
				true,
				(Calendar.getInstance()).getTime(),
				pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listEvent", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listGlobalEvent_pageNumber", pageNumber);
		request.getSession().setAttribute("listGlobalEvent_totalPages", totalPages);
		
		return "event/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listEvent(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listGlobalEvent_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listGlobalEvent_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/event/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "startDate"));
			
			Page<Event> page = this.eventRepo.findByPublishAndEndDateGreaterThanEqual(
					true,
					(Calendar.getInstance()).getTime(),
					pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listGlobalEvent_pageNumber", pageNumber);
			request.getSession().setAttribute("listGlobalEvent_totalPages", totalPages);
			
			model.addAttribute("listEvent", page.getContent());
			
			return "event/list";
		
		} catch(Exception e) {
			return "redirect:/event/list";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "events";
	}
}
