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
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.MatrimonialRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController extends HomeBaseController {
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private BusinessRepo businessRepo;
	
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
	
	
	@GetMapping("/home")
	public String indexView(Model model, Principal principal) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		List<Reminder> listReminder = this.reminderRepo.listReminder(member, Calendar.getInstance().getTime());
		
		model.addAttribute("listReminder", listReminder);
		
		return "home/default";
		
	}
}
