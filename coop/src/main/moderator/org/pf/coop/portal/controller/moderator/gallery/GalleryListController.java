package org.pf.coop.portal.controller.moderator.gallery;

import java.security.Principal;

import org.pf.coop.portal.controller.moderator.ModeratorBaseController;
import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.repository.GalleryRepo;
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
@RequestMapping("/moderator/gallery")
public class GalleryListController extends ModeratorBaseController {
	
	@Autowired
	private GalleryRepo galleryRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listGallery(@ModelAttribute Gallery gallery, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("moderatorSearch_gallery", gallery);
				
			return "redirect:/moderator/gallery/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listGallery(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Gallery> page;
			
			Gallery obj = (Gallery) request.getSession().getAttribute("moderatorSearch_gallery");
			
			if (obj == null) {
				page = this.galleryRepo.findAll(pageable);
				obj = new Gallery();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.galleryRepo.findAll(pageable);
				} else {
					page = this.galleryRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("moderatorSearch_gallery", obj);
			model.addAttribute("gallery", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listGallery", page.getContent());
			
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
			
			request.getSession().setAttribute("listGalleryModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listGalleryModerator_totalPages", totalPages);
			
			return "moderator/gallery/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listGallery(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listGalleryModerator_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listGalleryModerator_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/moderator/gallery/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Gallery> page;
			
			Gallery obj = (Gallery) request.getSession().getAttribute("moderatorSearch_gallery");
			
			if (obj == null) {
				page = this.galleryRepo.findAll(pageable);
				obj = new Gallery();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.galleryRepo.findAll(pageable);
				} else {
					page = this.galleryRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("moderatorSearch_gallery", obj);
			model.addAttribute("gallery", obj);
			
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
			
			request.getSession().setAttribute("listGalleryModerator_pageNumber", pageNumber);
			request.getSession().setAttribute("listGalleryModerator_totalPages", totalPages);
			
			model.addAttribute("listGallery", page.getContent());
			
			return "moderator/gallery/list";
		
		} catch(Exception e) {
			return "redirect:/moderator/gallery/list";
		}
	}
}
