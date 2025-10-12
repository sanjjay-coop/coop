package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MaritalStatusService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<MaritalStatus> oe = this.maritalStatusRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMaritalStatus(MaritalStatus obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = maritalStatusRepo.save(obj);
	
		audit = new Audit(updateBy, "MaritalStatus", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMaritalStatus(Long id, String updateBy) {

		Optional<MaritalStatus> oe = this.maritalStatusRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		MaritalStatus obj = oe.get();
		
		audit = new Audit(updateBy, "MaritalStatus", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		maritalStatusRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMaritalStatus(MaritalStatus maritalStatus, String updateBy) {
		
		Optional<MaritalStatus> oe = this.maritalStatusRepo.findById(maritalStatus.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		MaritalStatus obj = oe.get();
				
		obj.setStatus(maritalStatus.getStatus());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = maritalStatusRepo.save(obj);
		
		audit = new Audit(updateBy, "MaritalStatus", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
