package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Event;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EventService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private EventRepo eventRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Event> oe = this.eventRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addEvent(Event obj, String updateBy) {
		
		obj = eventRepo.save(obj);
	
		audit = new Audit(updateBy, "Event", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteEvent(Long id, String updateBy) {

		Optional<Event> oe = this.eventRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Event obj = oe.get();
		
		audit = new Audit(updateBy, "Event", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		eventRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateEvent(Event event, String updateBy) {
		
		Optional<Event> oe = this.eventRepo.findById(event.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Event obj = oe.get();
		
		obj.setDescription(event.getDescription());
		obj.setEndDate(event.getEndDate());
		obj.setEventType(event.getEventType());
		obj.setPublish(event.getPublish());
		obj.setStartDate(event.getStartDate());
		obj.setTitle(event.getTitle());
		
		obj = eventRepo.save(obj);
		
		audit = new Audit(updateBy, "Event", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
