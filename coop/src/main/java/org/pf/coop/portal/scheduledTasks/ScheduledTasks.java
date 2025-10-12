package org.pf.coop.portal.scheduledTasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Advert;
import org.pf.coop.portal.model.Article;
import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.model.Holiday;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.model.Language;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.model.NewsFeed;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.model.Organization;
import org.pf.coop.portal.model.Profession;
import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.model.Reminder;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.model.accounts.Expenditure;
import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.pf.coop.portal.model.accounts.Income;
import org.pf.coop.portal.model.library.Library;
import org.pf.coop.portal.model.library.TitleType;
import org.pf.coop.portal.repository.AdvertRepo;
import org.pf.coop.portal.repository.ArticleRepo;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.BulkEmailRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.pf.coop.portal.repository.CasteRepo;
import org.pf.coop.portal.repository.CategoryRepo;
import org.pf.coop.portal.repository.ContactRepo;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.pf.coop.portal.repository.EventTypeRepo;
import org.pf.coop.portal.repository.FundingRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.repository.HolidayRepo;
import org.pf.coop.portal.repository.InvitationRepo;
import org.pf.coop.portal.repository.JobRepo;
import org.pf.coop.portal.repository.LanguageRepo;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.pf.coop.portal.repository.MemberApplicationRepo;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.MenuItemRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.repository.NewsFeedRepo;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.repository.OrganizationRepo;
import org.pf.coop.portal.repository.ProfessionRepo;
import org.pf.coop.portal.repository.QuotationRepo;
import org.pf.coop.portal.repository.ReminderRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.pf.coop.portal.repository.SponsorshipApplicationRepo;
import org.pf.coop.portal.repository.SponsorshipRepo;
import org.pf.coop.portal.repository.TribeRepo;
import org.pf.coop.portal.repository.accounts.ExpenditureRepo;
import org.pf.coop.portal.repository.accounts.HeadOfAccountRepo;
import org.pf.coop.portal.repository.accounts.IncomeRepo;
import org.pf.coop.portal.repository.library.LibraryRepo;
import org.pf.coop.portal.repository.library.TitleRepo;
import org.pf.coop.portal.repository.library.TitleTypeRepo;
import org.pf.coop.portal.service.ReminderService;
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
	
	@Autowired
	private SponsorshipRepo sponsorshipRepo;
	
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
	private AdvertRepo advertRepo;
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private BusinessRepo businessRepo;
	
	@Autowired
	private JobRepo jobRepo;
	
	@Scheduled(fixedDelay = 1555200000)
	public void updateSearchString() {
		
		System.out.println("Updating search Strings.");

		List<Advert> listAdvert = this.advertRepo.findByRecordAddDateIsNull();
		
		for(Advert obj : listAdvert) {
			obj.setAddDefaults("system");
			advertRepo.save(obj);
		}

		List<Article> listArticle = this.articleRepo.findByRecordAddDateIsNull();
		
		for(Article obj : listArticle) {
			obj.setAddDefaults("system");
			articleRepo.save(obj);
		}

		List<BulkEmail> listBulkEmail = this.bulkEmailRepo.findByRecordAddDateIsNull();
		
		for(BulkEmail obj : listBulkEmail) {
			obj.setAddDefaults("system");
			bulkEmailRepo.save(obj);
		}

		List<Business> listBusiness = this.businessRepo.findByRecordAddDateIsNull();
		
		for(Business obj : listBusiness) {
			obj.setAddDefaults("system");
			businessRepo.save(obj);
		}

		List<Caste> listCaste = this.casteRepo.findByRecordAddDateIsNull();
		
		for(Caste obj : listCaste) {
			obj.setAddDefaults("system");
			casteRepo.save(obj);
		}

		List<Category> listCategory = this.categoryRepo.findByRecordAddDateIsNull();
		
		for(Category obj : listCategory) {
			obj.setAddDefaults("system");
			categoryRepo.save(obj);
		}

		List<Contact> listContact = this.contactRepo.findByRecordAddDateIsNull();
		
		for(Contact obj : listContact) {
			obj.setAddDefaults("system");
			contactRepo.save(obj);
		}

		List<EducationLevel> listEducationLevel = this.educationLevelRepo.findByRecordAddDateIsNull();
		
		for(EducationLevel obj : listEducationLevel) {
			obj.setAddDefaults("system");
			educationLevelRepo.save(obj);
		}

		List<Event> listEvent = this.eventRepo.findByRecordAddDateIsNull();
		
		for(Event obj : listEvent) {
			obj.setAddDefaults("system");
			eventRepo.save(obj);
		}

		List<EventType> listEventType = this.eventTypeRepo.findByRecordAddDateIsNull();
		
		for(EventType obj : listEventType) {
			obj.setAddDefaults("system");
			eventTypeRepo.save(obj);
		}

		List<Funding> listFunding = this.fundingRepo.findByRecordAddDateIsNull();
		
		for(Funding obj : listFunding) {
			obj.setAddDefaults("system");
			fundingRepo.save(obj);
		}

		List<Gender> listGender = this.genderRepo.findByRecordAddDateIsNull();
		
		for(Gender obj : listGender) {
			obj.setAddDefaults("system");
			genderRepo.save(obj);
		}

		List<Holiday> listHoliday = this.holidayRepo.findByRecordAddDateIsNull();
		
		for(Holiday obj : listHoliday) {
			obj.setAddDefaults("system");
			holidayRepo.save(obj);
		}

		List<Invitation> listInvitation = this.invitationRepo.findByRecordAddDateIsNull();
		
		for(Invitation obj : listInvitation) {
			obj.setAddDefaults("system");
			invitationRepo.save(obj);
		}

		List<Job> listJob = this.jobRepo.findByRecordAddDateIsNull();
		
		for(Job obj : listJob) {
			obj.setAddDefaults("system");
			jobRepo.save(obj);
		}

		List<Language> listLanguage = this.languageRepo.findByRecordAddDateIsNull();
		
		for(Language obj : listLanguage) {
			obj.setAddDefaults("system");
			languageRepo.save(obj);
		}

		List<MaritalStatus> listMaritalStatus = this.maritalStatusRepo.findByRecordAddDateIsNull();
		
		for(MaritalStatus obj : listMaritalStatus) {
			obj.setAddDefaults("system");
			maritalStatusRepo.save(obj);
		}

		List<Member> listMember = this.memberRepo.findByRecordAddDateIsNull();
		
		for(Member obj : listMember) {
			obj.setAddDefaults("system");
			memberRepo.save(obj);
		}

		List<MemberApplication> listMemberApplication = this.memberApplicationRepo.findByRecordAddDateIsNull();
		
		for(MemberApplication obj : listMemberApplication) {
			obj.setAddDefaults("system");
			memberApplicationRepo.save(obj);
		}

		List<MemberGroup> listMemberGroup = this.memberGroupRepo.findByRecordAddDateIsNull();
		
		for(MemberGroup obj : listMemberGroup) {
			obj.setAddDefaults("system");
			memberGroupRepo.save(obj);
		}

		List<MemberType> listMemberType = this.memberTypeRepo.findByRecordAddDateIsNull();
		
		for(MemberType obj : listMemberType) {
			obj.setAddDefaults("system");
			memberTypeRepo.save(obj);
		}

		List<MenuItem> listMenuItem = this.menuItemRepo.findByRecordAddDateIsNull();
		
		for(MenuItem obj : listMenuItem) {
			obj.setAddDefaults("system");
			menuItemRepo.save(obj);
		}

		List<MessageText> listMessageText = this.messageTextRepo.findByRecordAddDateIsNull();
		
		for(MessageText obj : listMessageText) {
			obj.setAddDefaults("system");
			messageTextRepo.save(obj);
		}

		List<NewsFeed> listNewsFeed = this.newsFeedRepo.findByRecordAddDateIsNull();
		
		for(NewsFeed obj : listNewsFeed) {
			obj.setAddDefaults("system");
			newsFeedRepo.save(obj);
		}

		List<Occupation> listOccupation = this.occupationRepo.findByRecordAddDateIsNull();
		
		for(Occupation obj : listOccupation) {
			obj.setAddDefaults("system");
			occupationRepo.save(obj);
		}

		List<Organization> listOrganization = this.organizationRepo.findByRecordAddDateIsNull();
		
		for(Organization obj : listOrganization) {
			obj.setAddDefaults("system");
			organizationRepo.save(obj);
		}

		List<Profession> listProfession = this.professionRepo.findByRecordAddDateIsNull();
		
		for(Profession obj : listProfession) {
			obj.setAddDefaults("system");
			professionRepo.save(obj);
		}

		List<Quotation> listQuotation = this.quotationRepo.findByRecordAddDateIsNull();
		
		for(Quotation obj : listQuotation) {
			obj.setAddDefaults("system");
			quotationRepo.save(obj);
		}

		List<Reminder> listReminder = this.reminderRepo.findByRecordAddDateIsNull();
		
		for(Reminder obj : listReminder) {
			obj.setAddDefaults("system");
			reminderRepo.save(obj);
		}

		List<Role> listRole = this.roleRepo.findByRecordAddDateIsNull();
		
		for(Role obj : listRole) {
			obj.setAddDefaults("system");
			roleRepo.save(obj);
		}

		List<Salutation> listSalutation = this.salutationRepo.findByRecordAddDateIsNull();
		
		for(Salutation obj : listSalutation) {
			obj.setAddDefaults("system");
			salutationRepo.save(obj);
		}

		List<Sponsorship> listSponsorship = this.sponsorshipRepo.findByRecordAddDateIsNull();
		
		for(Sponsorship obj : listSponsorship) {
			obj.setAddDefaults("system");
			sponsorshipRepo.save(obj);
		}

		List<SponsorshipApplication> listSponsorshipApplication = this.sponsorshipApplicationRepo.findByRecordAddDateIsNull();
		
		for(SponsorshipApplication obj : listSponsorshipApplication) {
			obj.setAddDefaults("system");
			sponsorshipApplicationRepo.save(obj);
		}

		List<Tribe> listTribe = this.tribeRepo.findByRecordAddDateIsNull();
		
		for(Tribe obj : listTribe) {
			obj.setAddDefaults("system");
			tribeRepo.save(obj);
		}

		List<Expenditure> listExpenditure = this.expenditureRepo.findByRecordAddDateIsNull();
		
		for(Expenditure obj : listExpenditure) {
			obj.setAddDefaults("system");
			expenditureRepo.save(obj);
		}

		List<Income> listIncome = this.incomeRepo.findByRecordAddDateIsNull();
		
		for(Income obj : listIncome) {
			obj.setAddDefaults("system");
			incomeRepo.save(obj);
		}

		List<HeadOfAccount> listHeadOfAccount = this.headOfAccountRepo.findByRecordAddDateIsNull();
		
		for(HeadOfAccount obj : listHeadOfAccount) {
			obj.setAddDefaults("system");
			headOfAccountRepo.save(obj);
		}

		List<Library> listLibrary = this.libraryRepo.findByRecordAddDateIsNull();
		
		for(Library obj : listLibrary) {
			obj.setAddDefaults("system");
			libraryRepo.save(obj);
		}

		List<TitleType> listTitleType = this.titleTypeRepo.findByRecordAddDateIsNull();
		
		for(TitleType obj : listTitleType) {
			obj.setAddDefaults("system");
			titleTypeRepo.save(obj);
		}
		
	}
	
	@Autowired
	TitleTypeRepo titleTypeRepo;
	
	@Autowired
	TitleRepo titleRepo;
	
	@Autowired
	LibraryRepo libraryRepo;
	
	@Autowired
	HeadOfAccountRepo headOfAccountRepo;
	
	@Autowired
	IncomeRepo incomeRepo;
	
	@Autowired
	ExpenditureRepo expenditureRepo;
	
	@Autowired
	TribeRepo tribeRepo;
	
	@Autowired
	SponsorshipApplicationRepo sponsorshipApplicationRepo;
	
	@Autowired
	SalutationRepo salutationRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	QuotationRepo quotationRepo;
	
	@Autowired
	ProfessionRepo professionRepo;
	
	@Autowired
	OrganizationRepo organizationRepo;
	
	@Autowired
	private OccupationRepo occupationRepo;
	
	@Autowired
	private NewsFeedRepo newsFeedRepo;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private MenuItemRepo menuItemRepo;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberApplicationRepo memberApplicationRepo;
	
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	
	@Autowired
	private LanguageRepo languageRepo;
	
	@Autowired
	private InvitationRepo invitationRepo;
	
	@Autowired
	private HolidayRepo holidayRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private FundingRepo fundingRepo;
	
	@Autowired
	private EventTypeRepo eventTypeRepo;
	
	@Autowired
	private EventRepo eventRepo;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private CasteRepo casteRepo;
}
