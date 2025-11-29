package org.pf.coop.portal.controller.mobile.home.reminder;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Reminder;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ReminderRepo;
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
@RequestMapping("/mobile/home/reminder")
public class MobileHomeReminderListController extends MobileBaseController {
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private ReminderRepo reminderRepo;
	
	@GetMapping("/list")
	public String listReminder(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "remDate"));
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		Page<Reminder> page = this.reminderRepo.findByMember(member, pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listReminder", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listReminder_pageNumber", pageNumber);
		request.getSession().setAttribute("listReminder_totalPages", totalPages);
		
		return "mobile/home/reminder/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listReminder(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listReminder_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listReminder_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/reminder/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "remDate"));
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Page<Reminder> page = this.reminderRepo.findByMember(member, pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listReminder_pageNumber", pageNumber);
			request.getSession().setAttribute("listReminder_totalPages", totalPages);
			
			model.addAttribute("listReminder", page.getContent());
			
			return "mobile/home/reminder/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/reminder/list";
		}
	}
}
