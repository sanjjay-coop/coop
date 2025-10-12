package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SalutationService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private SalutationRepo salutationRepo;
	
	private Audit audit;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Salutation> oe = this.salutationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSalutation(Salutation obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = salutationRepo.save(obj);
		
		audit = new Audit(updateBy, "Salutation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSalutation(Long id, String updateBy) {

		Optional<Salutation> oe = this.salutationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Salutation obj = oe.get();
		
		audit = new Audit(updateBy, "Salutation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		salutationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSalutation(Salutation salutation, String updateBy) {
		
		Optional<Salutation> oe = this.salutationRepo.findById(salutation.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Salutation obj = oe.get();
		obj.setName(salutation.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = salutationRepo.save(obj);
		
		audit = new Audit(updateBy, "Salutation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

