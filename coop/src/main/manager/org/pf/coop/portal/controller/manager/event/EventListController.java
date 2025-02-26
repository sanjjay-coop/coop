package org.pf.coop.portal.controller.manager.event;

import java.security.Principal;

import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.repository.EventRepo;
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
@RequestMapping("/manager/event")
public class EventListController extends ManagerBaseController {
	
	@Autowired
	private EventRepo eventRepo;
	
	@GetMapping("/list")
	public String listEvent(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "startDate"));
		
		Page<Event> page = this.eventRepo.findAll(pageable);
		
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
		
		request.getSession().setAttribute("listEvent_pageNumber", pageNumber);
		request.getSession().setAttribute("listEvent_totalPages", totalPages);
		
		return "manager/event/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listEvent(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listEvent_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listEvent_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/event/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "startDate"));
			
			Page<Event> page = this.eventRepo.findAll(pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listEvent_pageNumber", pageNumber);
			request.getSession().setAttribute("listEvent_totalPages", totalPages);
			
			model.addAttribute("listEvent", page.getContent());
			
			return "manager/event/list";
		
		} catch(Exception e) {
			return "redirect:/manager/event/list";
		}
	}
}

