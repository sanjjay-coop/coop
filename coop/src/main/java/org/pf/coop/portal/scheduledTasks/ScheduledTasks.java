package org.pf.coop.portal.scheduledTasks;

import java.util.Calendar;
import java.util.List;

import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.BulkEmailRepo;
import org.pf.coop.portal.repository.ReminderRepo;
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
}
