package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Reminder;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ReminderService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	private Audit audit;
	
	@Autowired
	private ReminderRepo reminderRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Member> oe = this.memberRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addReminder(Reminder obj, String updateBy) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(obj.getRemDate());
		cal.add(Calendar.DAY_OF_YEAR, -1*obj.getRemDays().intValue());
		
		obj.setRemStartDate(cal.getTime());
		
		obj = reminderRepo.save(obj);
		
		audit = new Audit(updateBy, "Reminder", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteReminder(Long id, String updateBy) {

		Optional<Reminder> oe = this.reminderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Reminder obj = oe.get();
		
		if (!updateBy.equals(obj.getMember().getMemId())) {
			return new TransactionResult(false, "Record does not belong to the you.");
		}
		
		audit = new Audit(updateBy, "Reminder", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		reminderRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	
	public void addReminders() {
		
		Calendar cal1 = Calendar.getInstance();
		
		cal1.add(Calendar.DAY_OF_YEAR, 9);
		
		Calendar cal2 = Calendar.getInstance();
		
		cal2.add(Calendar.DAY_OF_YEAR, 10);
		
		List<Member> listMember = this.memberRepo.findAllBySubEndDateBetween(cal1.getTime(), cal2.getTime());
		
		for(Member member : listMember) {
			Reminder reminder = new Reminder();
			
			reminder.setTitle("Subscription renewal reminder");
			reminder.setDescription("Your subscription is due to expire on "
					+ member.getSubEndDate().toString()
					+ ". Kindly renew it at your earliest convenience."
					);
			reminder.setRemByEmail(true);
			reminder.setRemBySelf(false);
			reminder.setRemDate(member.getSubEndDate());
			reminder.setRemDays(Long.valueOf(5));
			reminder.setMember(member);
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(member.getSubEndDate());
			cal.add(Calendar.DAY_OF_YEAR, -5);
			
			reminder.setRemStartDate(cal.getTime());
			
			reminderRepo.save(reminder);
		}
	}
}

