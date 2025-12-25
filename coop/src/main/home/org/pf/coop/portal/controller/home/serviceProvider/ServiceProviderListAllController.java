package org.pf.coop.portal.controller.home.serviceProvider;

import java.security.Principal;
import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.ServiceProviderRepo;
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
@RequestMapping("/home/serviceProvider/all")
public class ServiceProviderListAllController extends HomeBaseController {
	
	@Autowired
	private EntityManager entityManager;
	
	private String errorMessage;
	private int resultSize = 10;
	private long pageNumber = 0;
	private long totalRecords = 0;
	private long totalPages = 0;
	private List<ServiceProvider> listServiceProvider;
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	private Boolean validObject(ServiceProvider obj) {
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
	public String listServiceProvider(@ModelAttribute ServiceProvider serviceProvider, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			if (!this.validObject(serviceProvider)) reat.addFlashAttribute("message", this.errorMessage); 
				
			request.getSession().setAttribute("homeSearchAll_serviceProvider", serviceProvider);
				
			return "redirect:/home/serviceProvider/all/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listServiceProvider(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		this.pageNumber = 0;
		
		ServiceProvider obj = (ServiceProvider) request.getSession().getAttribute("homeSearchAll_serviceProvider");

		if (obj == null) { obj = new ServiceProvider();} // obj was null
		
		this.searchServiceProvider(obj);
		
		model.addAttribute("currentPage", this.pageNumber + 1);
		model.addAttribute("totalPages", this.totalPages);

		model.addAttribute("totalRecords", this.totalRecords);
		
		request.getSession().setAttribute("homeSearchAll_serviceProvider", obj);
		model.addAttribute("serviceProvider", obj);
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listServiceProviderAll_pageNumber", this.pageNumber);
		request.getSession().setAttribute("listServiceProviderAll_totalPages", this.totalPages);
		
		model.addAttribute("listServiceProvider", this.listServiceProvider);
		
		return "home/serviceProvider/listAll";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listServiceProvider(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			this.pageNumber = (int) request.getSession().getAttribute("listServiceProviderAll_pageNumber");
			this.totalPages = (int) request.getSession().getAttribute("listServiceProviderAll_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/serviceProvider/all/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			ServiceProvider obj = (ServiceProvider) request.getSession().getAttribute("homeSearchAll_serviceProvider");
			
			if (obj == null) { obj = new ServiceProvider(); }
			
			this.searchServiceProvider(obj);
			
			model.addAttribute("currentPage", this.pageNumber + 1);
			model.addAttribute("totalPages", this.totalPages);

			model.addAttribute("totalRecords", this.totalRecords);
			
			request.getSession().setAttribute("homeSearchAll_serviceProvider", obj);
			model.addAttribute("serviceProvider", obj);
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listServiceProviderAll_pageNumber", this.pageNumber);
			request.getSession().setAttribute("listServiceProviderAll_totalPages", this.totalPages);
			
			model.addAttribute("listServiceProvider", this.listServiceProvider);
			
			return "home/serviceProvider/listAll";
		
		} catch(Exception e) {
			return "redirect:/home/serviceProvider/all/list";
		}
	}
	
	private void searchServiceProvider(ServiceProvider obj) {
		
		if (this.validObject(obj)) {
			
			final String str = obj.getSearchFor();
			
			SearchSession searchSession = Search.session(this.entityManager);
			
			SearchResult<ServiceProvider> result = searchSession.search(ServiceProvider.class)
					.where(f -> f.bool()
							.must(f.match().field("searchString").matching(str))
							.must(f.match().field("enabled").matching(true))
							)
					.fetch((int) this.pageNumber, this.resultSize);
			
			this.totalRecords = result.total().hitCount();
			this.totalPages = this.getTotalPages(totalRecords);
			
			this.listServiceProvider = result.hits();
			
		} else {
			
			Pageable pageable = PageRequest.of((int) this.pageNumber, this.resultSize, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<ServiceProvider> page = this.serviceProviderRepo.findByEnabled(
					true, 
					pageable);
			
			totalRecords = page.getTotalElements();
			totalPages = page.getTotalPages();
			
			this.listServiceProvider = page.getContent();
		}
	}
}

