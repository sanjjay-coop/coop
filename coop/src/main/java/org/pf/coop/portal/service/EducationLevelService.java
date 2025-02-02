package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EducationLevelService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<EducationLevel> oe = this.educationLevelRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addEducationLevel(EducationLevel obj, String updateBy) {
		
		obj = educationLevelRepo.save(obj);
		
		audit = new Audit(updateBy, "EducationLevel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteEducationLevel(Long id, String updateBy) {

		Optional<EducationLevel> oe = this.educationLevelRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		EducationLevel obj = oe.get();
		
		audit = new Audit(updateBy, "EducationLevel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		educationLevelRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateEducationLevel(EducationLevel educationLevel, String updateBy) {
		
		Optional<EducationLevel> oe = this.educationLevelRepo.findById(educationLevel.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		EducationLevel obj = oe.get();
		
		obj.setName(educationLevel.getName());
		
		obj = educationLevelRepo.save(obj);
		
		audit = new Audit(updateBy, "EducationLevel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

