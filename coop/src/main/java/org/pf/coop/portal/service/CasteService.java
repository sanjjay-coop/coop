package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.CasteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CasteService {
	
	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;

	@Autowired
	private CasteRepo casteRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Caste> oe = this.casteRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addCaste(Caste obj, String updateBy) {
		
		obj = casteRepo.save(obj);
		
		audit = new Audit(updateBy, "Caste", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteCaste(Long id, String updateBy) {

		Optional<Caste> oe = this.casteRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Caste obj = oe.get();
		
		audit = new Audit(updateBy, "Caste", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		casteRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateCaste(Caste caste, String updateBy) {
		
		Optional<Caste> oe = this.casteRepo.findById(caste.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Caste obj = oe.get();
		
		obj.setName(caste.getName());
		
		obj = casteRepo.save(obj);
		
		audit = new Audit(updateBy, "Caste", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}


