package org.pf.coop.portal.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Carousel;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.model.NewsFeed;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.CarouselRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.pf.coop.portal.repository.GalleryRepo;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.NewsFeedRepo;
import org.pf.coop.portal.repository.library.TitleRepo;
import org.pf.coop.portal.service.ManagerService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController extends IndexBaseController {
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private EventRepo eventRepo;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private CarouselRepo carouselRepo;
	
	@Autowired
	private TitleRepo titleRepo;
	
	@Autowired
	private NewsFeedRepo newsFeedRepo;
	
	@ModelAttribute("countArticle")
	public long getCountArticle(){
		return this.articleRepo.count();
	}
	
	@ModelAttribute("countBusiness")
	public long getCountBusiness(){
		return this.businessRepo.count();
	}
	
	@ModelAttribute("countJob")
	public long getCountJob(){
		return this.jobRepo.count();
	}
	
	@ModelAttribute("countNewsFeed")
	public long getCountNewsFeed(){
		return this.newsFeedRepo.count();
	}
	
	@ModelAttribute("countMember")
	public long getCountMember(){
		return this.memberRepo.count();
	}
	
	@ModelAttribute("countTitle")
	public long getCountTitle(){
		return this.titleRepo.count();
	}
	
	@Autowired
	private GalleryRepo galleryRepo;
	
	@ModelAttribute("listHomeGallery")
	public List<Gallery> getListGallery(){
		
		Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "date"));
		
		Page<Gallery> page = this.galleryRepo.findAll(pageable);
		
		return page.getContent();
	}
	
	@GetMapping("/")
	public String indexView(Model model, Principal principal) {
		
		if (this.memberRepo.count() < 1) {
			this.managerService.initiate();
		}
		
		return "index";
		
	}
	
	@GetMapping("/error")
	public String error(Model model, RedirectAttributes reat, Principal principal) {
		
		return "hello";
		
	}
	
	@GetMapping("/accessDenied")
	public String errorView(Model model, Principal principal) {
		
		return "accessDenied";
		
	}
	
	@GetMapping("/event/view/{id}")
	public String viewEvent(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Event event = this.eventRepo.getReferenceById(id);
			
			if (event == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/";
			}
	
			model.addAttribute("event", event);
			
			return "event/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/";
		}
	}
	
	@GetMapping("/article/view/{id}")
	public String viewArticle(@PathVariable Long id, Model model,
			RedirectAttributes reat) {

		try {
			Article article = this.articleRepo.getReferenceById(id);
			
			if (article == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/";
			}
	
			model.addAttribute("article", article);
			
			return "article/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/";
		}
	}
	
	@ModelAttribute("carouselActive")
	public Carousel getCarouselActive(){
		List<Carousel> listCarousel = this.carouselRepo.listCarouselForPublication(Calendar.getInstance().getTime());
		
		if (listCarousel.isEmpty()) return null;
		else {
			return listCarousel.get(0);
		}
	}
	
	@ModelAttribute("listCarouselDisplay")
	public List<Carousel> getCarouselPhotos(){
		List<Carousel> listCarousel = this.carouselRepo.listCarouselForPublication(Calendar.getInstance().getTime());
		
		if (listCarousel.isEmpty()) return listCarousel;
		else {
			listCarousel.remove(0);
			return listCarousel;
		}
	}
	

	@ModelAttribute("listNewsFeedHome")
	public List<NewsFeed> getNewsFeed(){
		List<NewsFeed> listNewsFeedHome = this.newsFeedRepo.listNewsFeedRecent();

		return listNewsFeedHome;
	}
}