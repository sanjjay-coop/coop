package org.pf.coop.portal.service;

import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AuditService {

	@Autowired
	private AuditRepo auditRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Audit> oe = this.auditRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAudit(Audit obj, String updateBy) {
		
		obj = auditRepo.save(obj);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAudit(Long id, String updateBy) {

		Optional<Audit> oe = this.auditRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Audit obj = oe.get();
		
		auditRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAudit(Audit audit, String updateBy) {
		
		Optional<Audit> oe = this.auditRepo.findById(audit.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Audit obj = oe.get();
		
		obj = auditRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}
}

