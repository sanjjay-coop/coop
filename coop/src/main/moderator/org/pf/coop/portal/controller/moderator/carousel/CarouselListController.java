package org.pf.coop.portal.controller.moderator.carousel;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Carousel;
import org.pf.coop.portal.repository.CarouselRepo;
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
@RequestMapping("/moderator/carousel")
public class CarouselListController extends ModeratorBaseController {
	
	@Autowired
	private CarouselRepo carouselRepo;
	
	@GetMapping("/list")
	public String listCarousel(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<Carousel> page = this.carouselRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listCarousel", page.getContent());
		
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
		
		request.getSession().setAttribute("listCarousel_pageNumber", pageNumber);
		request.getSession().setAttribute("listCarousel_totalPages", totalPages);
		
		return "moderator/carousel/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listCarousel(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listCarousel_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listCarousel_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/carousel/list";
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
			
			Page<Carousel> page = this.carouselRepo.findAll(pageable);
			
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
			
			request.getSession().setAttribute("listCarousel_pageNumber", pageNumber);
			request.getSession().setAttribute("listCarousel_totalPages", totalPages);
			
			model.addAttribute("listCarousel", page.getContent());
			
			return "moderator/carousel/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/carousel/list";
		}
	}
}
