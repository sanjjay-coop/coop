package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.EventTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EventTypeService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private EventTypeRepo eventTypeRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<EventType> oe = this.eventTypeRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addEventType(EventType obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = eventTypeRepo.save(obj);
		
		audit = new Audit(updateBy, "EventType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteEventType(Long id, String updateBy) {

		Optional<EventType> oe = this.eventTypeRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		EventType obj = oe.get();
		
		audit = new Audit(updateBy, "EventType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		eventTypeRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}

	@Transactional
	public TransactionResult updateEventType(EventType eventType, String updateBy) {
		
		Optional<EventType> oe = this.eventTypeRepo.findById(eventType.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		EventType obj = oe.get();
		
		obj.setName(eventType.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = eventTypeRepo.save(obj);
		
		audit = new Audit(updateBy, "EventType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

