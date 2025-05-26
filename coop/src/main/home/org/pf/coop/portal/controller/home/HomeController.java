package org.pf.coop.portal.controller.home;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Reminder;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.MatrimonialRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ReminderRepo;
import org.pf.coop.portal.repository.library.TitleRepo;
import org.pf.coop.portal.service.BusinessService;
import org.pf.coop.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController extends HomeBaseController {
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private EventRepo eventRepo;
	
	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private MatrimonialRepo matrimonialRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private ReminderRepo reminderRepo;
	
	@Autowired
	private TitleRepo titleRepo;
	
	@ModelAttribute("listArticle")
	public List<Article> getListArticle(){
		return (List<Article>) this.articleRepo.listArticleRecent(Calendar.getInstance().getTime());
	}
	
	@ModelAttribute("listEvent")
	public List<Event> getListEvent(){
		return (List<Event>) this.eventRepo.listEventRecent(Calendar.getInstance().getTime());
	}
	
	@ModelAttribute("listMember")
	public List<Member> getListMember(){
		return (List<Member>) this.memberRepo.listMemberRecent();
	}
	
	@ModelAttribute("listBusiness")
	public List<Business> getListBusiness(){
		return (List<Business>) this.businessRepo.listBusinessRecent();
	}
	
	@ModelAttribute("listJob")
	public List<Job> getListJob(){
		return (List<Job>) this.jobRepo.listJobRecent();
	}
	
	@ModelAttribute("listMatrimonial")
	public List<Matrimonial> getListMatrimonial(){
		return (List<Matrimonial>) this.matrimonialRepo.listMatrimonialRecent();
	}
	
	@ModelAttribute("listTitle")
	public List<Title> getListTitle(){
		return (List<Title>) this.titleRepo.listTitleRecent();
	}
	
	
	@GetMapping("/home")
	public String indexView(Model model, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		List<Reminder> listReminder = this.reminderRepo.listReminder(member, Calendar.getInstance().getTime());
		
		model.addAttribute("listReminder", listReminder);
		
		return "home/default";
		
	}
	
	@GetMapping("/home/business/photo/{id}")
	public ResponseEntity<byte[]> getBusinessFile(@PathVariable Long id) {
	    
		Business business = (Business) this.businessService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + business.getFileName() + "\"")
	        .body(business.getFileData());
	}
	
	@GetMapping("/home/job/file/{id}")
	public ResponseEntity<byte[]> getJobFile(@PathVariable Long id) {
	    
		Job job = (Job) this.jobService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + job.getFileName() + "\"")
	        .body(job.getFileData());
	}
}
