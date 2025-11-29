package org.pf.coop.portal.controller.mobile.home.serviceProvider;

import java.security.Principal;

import org.pf.coop.portal.controller.mobile.MobileBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ServiceProviderRepo;
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
@RequestMapping("/mobile/home/serviceProvider")
public class MobileHomeMyServiceProviderListController extends MobileBaseController {
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/list")
	public String listServiceProvider(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "serviceName"));
		
		Page<ServiceProvider> page = this.serviceProviderRepo.findByOwner(member, pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listServiceProvider", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listServiceProvider_pageNumber", pageNumber);
		request.getSession().setAttribute("listServiceProvider_totalPages", totalPages);
		
		model.addAttribute("serviceProvider", new ServiceProvider());
		
		return "mobile/home/serviceProvider/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listServiceProvider(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listServiceProvider_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listServiceProvider_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/mobile/home/serviceProvider/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "serviceName"));
			
			Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			Page<ServiceProvider> page = this.serviceProviderRepo.findByOwner(member, pageable);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listServiceProvider_pageNumber", pageNumber);
			request.getSession().setAttribute("listServiceProvider_totalPages", totalPages);
			
			model.addAttribute("listServiceProvider", page.getContent());
			
			model.addAttribute("serviceProvider", new ServiceProvider());
			
			return "mobile/home/serviceProvider/list";
		
		} catch(Exception e) {
			return "redirect:/mobile/home/serviceProvider/list";
		}
	}
}
