package org.pf.coop.portal.scheduledTasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.BulkEmailRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ReminderRepo;
import org.pf.coop.portal.repository.SponsorshipRepo;
import org.pf.coop.portal.service.ArticleService;
import org.pf.coop.portal.service.BusinessService;
import org.pf.coop.portal.service.FundingService;
import org.pf.coop.portal.service.InvitationService;
import org.pf.coop.portal.service.JobService;
import org.pf.coop.portal.service.MemberApplicationService;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.service.QuotationService;
import org.pf.coop.portal.service.ReminderService;
import org.pf.coop.portal.service.SponsorshipApplicationService;
import org.pf.coop.portal.service.SponsorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {

	@Autowired
	private ReminderRepo reminderRepo;
	
	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BulkEmailRepo bulkEmailRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Scheduled(fixedDelay = 86400000)
	public void purgeOldRecords() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.YEAR, -1);
		
		System.out.println("Purge Old Records: Audit - More than 1 year old.");
		
		this.auditRepo.deleteOldRecords(cal.getTime());
		
		System.out.println("Purge Old Records: Reminders - More than 1 year old.");
		
		this.reminderRepo.deleteOldRecords(cal.getTime());

		System.out.println("Add Reminders: Reminders for subscription renewal.");
		
		this.reminderService.addReminders();
	}
	
	@Scheduled(cron = "0 40 7 * * *")
	public void sentBulkEmail() {
		
		List<BulkEmail> listbe = this.bulkEmailRepo.listBulkEmailForSending();
		if (!listbe.isEmpty()) {
			BulkEmail bulkEmail = listbe.get(0);
			if (bulkEmail == null) {
				System.out.println("BulkEmail: Nothing to Send.");
			} else {
				this.emailService.sendEmail(bulkEmail);
				try {
					bulkEmail.setStatus("Y");
					bulkEmail.setDateSent(Calendar.getInstance().getTime());
					this.bulkEmailRepo.save(bulkEmail);
				} catch(Exception e) {
					System.out.println("BulkEmail: " + e.getMessage());
				}
			}
		}
	}
	
	@Scheduled(cron = "0 0 0 13 * *")
	public void sendBulletin() {
		
		String str = "";
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		
		Date date = cal.getTime();
		
		int i = 0;
		
		List<Article> listArticle = this.articleRepo.listArticleForBulletin(date, Calendar.getInstance().getTime());
		
		if (!listArticle.isEmpty()) { 
			str = str + "\n\n" + "New Articles\n";
			int count = 1;
			for(Article article : listArticle) {
				str = str + "\n" + count++ + article.getTitle() + " [ " + article.getAuthor() + " ] ";
			}
			i++;
		}
		
		List<Business> listBusiness = this.businessRepo.listBusinessForBulletin(date);
		
		if (!listBusiness.isEmpty()) { 
			str = str + "\n\n" + "New Businesses\n";
			int count = 1;
			for(Business business : listBusiness) {
				str = str + "\n" + count++ + business.getBusinessName() + " [ " + business.getOwner().getName() + " ] ";
			}
			i++;
		}
		
		List<Job> listJob = this.jobRepo.listJobForBulletin(date);
		
		if (!listJob.isEmpty()) { 
			str = str + "\n\n" + "New Job Vacancies\n";
			int count = 1;
			for(Job job : listJob) {
				str = str + "\n" + count++ + job.getPosition() + " [ " + job.getFirmName() + ", " + job.getCity()+ " ] ";
			}
			i++;
		}
		
		List<Sponsorship> listSponsorship = this.sponsorshipRepo.listSponsorshipForBulletin(date, Calendar.getInstance().getTime());
		
		if (!listSponsorship.isEmpty()) { 
			str = str + "\n\n" + "New Sponsorships\n";
			int count = 1;
			for(Sponsorship sponsorship : listSponsorship) {
				str = str + "\n" + count++ + sponsorship.getTitle();
			}
			i++;
		}
		
		if (i>0) {
			List<Member> listMember = this.memberRepo.findBySubEndDateGreaterThan(Calendar.getInstance().getTime());
			
			String emails = "";
			
			if (!listMember.isEmpty()) {
				for (Member member : listMember) {
					emails = member.getEmail() + ", ";
				}
			}
			
			emails = emails.substring(0, emails.length()-3);
			
			this.emailService.sendEmail(emails, str);
		}
	}
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private FundingService fundingService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberApplicationService memberApplicationService;
	
	@Autowired
	private QuotationService quotationService;
	
	@Autowired
	private SponsorshipRepo sponsorshipRepo;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private SponsorshipApplicationService sponsorshipApplicationService;
	
	@Scheduled(fixedDelay = 1555200000)
	public void updateSearchString() {
		
		this.articleService.updateSearchString();
		
		this.businessService.updateSearchString();
		
		this.fundingService.updateSearchString();
		
		this.invitationService.updateSearchString();
		
		this.jobService.updateSearchString();
		
		this.memberService.updateSearchString();
		
		this.memberApplicationService.updateSearchString();
		
		this.quotationService.updateSearchString();
		
		this.sponsorshipService.updateSearchString();
		
		this.sponsorshipApplicationService.updateSearchString();
		
	}
}
