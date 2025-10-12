package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class GenderService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private GenderRepo genderRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Gender> oe = this.genderRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addGender(Gender obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = genderRepo.save(obj);
	
		audit = new Audit(updateBy, "Gender", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteGender(Long id, String updateBy) {

		Optional<Gender> oe = this.genderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Gender obj = oe.get();
		
		audit = new Audit(updateBy, "Gender", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		genderRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateGender(Gender gender, String updateBy) {
		
		Optional<Gender> oe = this.genderRepo.findById(gender.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Gender obj = oe.get();
		
		obj.setName(gender.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = genderRepo.save(obj);
		
		audit = new Audit(updateBy, "Gender", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

