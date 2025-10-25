package org.pf.coop.portal.controller.moderator.bulkEmail;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.repository.BulkEmailRepo;
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

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/moderator/bulkEmail")
public class BulkEmailListController extends ModeratorBaseController {
	
	@Autowired
	private BulkEmailRepo bulkEmailRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listBulkEmail(@ModelAttribute BulkEmail bulkEmail, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_bulkEmail", bulkEmail);
				
			return "redirect:/moderator/bulkEmail/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listBulkEmail(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<BulkEmail> page;
			
			BulkEmail obj = (BulkEmail) request.getSession().getAttribute("moderatorSearch_bulkEmail");
			
			if (obj == null) {
				page = this.bulkEmailRepo.findAll(pageable);
				obj = new BulkEmail();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.bulkEmailRepo.findAll(pageable);
				} else {
					page = this.bulkEmailRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_bulkEmail", obj);
			model.addAttribute("bulkEmail", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("totalRecords", page.getTotalElements());
			
			model.addAttribute("listBulkEmail", page.getContent());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listBulkEmailModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listBulkEmailModerator_totalPages", totalPages);
			
			return "moderator/bulkEmail/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listBulkEmail(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listBulkEmailModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listBulkEmailModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/bulkEmail/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<BulkEmail> page;
			
			BulkEmail obj = (BulkEmail) request.getSession().getAttribute("moderatorSearch_bulkEmail");
			
			if (obj == null) {
				page = this.bulkEmailRepo.findAll(pageable);
				obj = new BulkEmail();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.bulkEmailRepo.findAll(pageable);
				} else {
					page = this.bulkEmailRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_bulkEmail", obj);
			model.addAttribute("bulkEmail", obj);
			
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
			
			request.getSession().setAttribute("listBulkEmailModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listBulkEmailModerator_totalPages", totalPages);
			
			model.addAttribute("listBulkEmail", page.getContent());
			
			return "moderator/bulkEmail/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/bulkEmail/list";
		}
	}
}


