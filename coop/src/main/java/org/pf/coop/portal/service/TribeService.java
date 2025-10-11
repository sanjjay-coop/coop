package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.TribeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TribeService {
	
	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;

	@Autowired
	private TribeRepo tribeRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Tribe> oe = this.tribeRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addTribe(Tribe obj, String updateBy) {
		
		obj = tribeRepo.save(obj);
		
		audit = new Audit(updateBy, "Tribe", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteTribe(Long id, String updateBy) {

		Optional<Tribe> oe = this.tribeRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Tribe obj = oe.get();
		
		audit = new Audit(updateBy, "Tribe", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		tribeRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateTribe(Tribe tribe, String updateBy) {
		
		Optional<Tribe> oe = this.tribeRepo.findById(tribe.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Tribe obj = oe.get();
		
		obj.setName(tribe.getName());
		
		obj = tribeRepo.save(obj);
		
		audit = new Audit(updateBy, "Tribe", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}



