package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.EventUpdate;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.EventUpdateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EventUpdateService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private EventUpdateRepo eventUpdateRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<EventUpdate> oe = this.eventUpdateRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addEventUpdate(EventUpdate obj, String updateBy) {
		
		obj.setUpdateDate(Calendar.getInstance().getTime());
		
		obj = eventUpdateRepo.save(obj);
	
		audit = new Audit(updateBy, "EventUpdate", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteEventUpdate(Long id, String updateBy) {

		Optional<EventUpdate> oe = this.eventUpdateRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		EventUpdate obj = oe.get();
		
		audit = new Audit(updateBy, "EventUpdate", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		eventUpdateRepo.deleteById(obj.getId());

		return new TransactionResult(true, "Record deleted successfully");
	}
}

