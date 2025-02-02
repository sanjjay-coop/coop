package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Matrimonial;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MatrimonialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MatrimonialService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MatrimonialRepo matrimonialRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Matrimonial> oe = this.matrimonialRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMatrimonial(Matrimonial obj, String updateBy) {
		
		obj.setAddDate(Calendar.getInstance().getTime());
		obj.setEnabled(false);
		
		obj = matrimonialRepo.save(obj);
	
		audit = new Audit(updateBy, "Matrimonial", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMatrimonial(Long id, String updateBy) {

		Optional<Matrimonial> oe = this.matrimonialRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Matrimonial obj = oe.get();
		
		audit = new Audit(updateBy, "Matrimonial", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		matrimonialRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMatrimonial(Matrimonial matrimonial, String updateBy) {
		
		Optional<Matrimonial> oe = this.matrimonialRepo.findById(matrimonial.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Matrimonial obj = oe.get();
			
		obj.setAge(matrimonial.getAge());
		obj.setDescription(matrimonial.getDescription());
		obj.setEducationLevel(matrimonial.getEducationLevel());
		obj.setEmail(matrimonial.getEmail());
		obj.setExpDescription(matrimonial.getExpDescription());
		obj.setExpMaxAge(matrimonial.getExpMaxAge());
		obj.setExpMinAge(matrimonial.getExpMinAge());
		obj.setExpOccupation(matrimonial.getExpOccupation());
		obj.setLookingFor(matrimonial.getLookingFor());
		obj.setMobile(matrimonial.getMobile());
		obj.setOccupation(matrimonial.getOccupation());
		obj.setOwner(matrimonial.getOwner());
		
		obj = matrimonialRepo.save(obj);
		
		audit = new Audit(updateBy, "Matrimonial", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult enableDisableMatrimonial(Long id, Boolean status, String updateBy) {

		Optional<Matrimonial> oe = this.matrimonialRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Matrimonial obj = oe.get();
		
		obj.setEnabled(status);
		
		audit = new Audit(updateBy, "Matrimonial", status.toString(), obj.getId(), Calendar.getInstance().getTime(), "ENABLE/DISABLE");
		auditRepo.save(audit);
		
		matrimonialRepo.save(obj);

		return new TransactionResult(true, "Record updated successfully");
	}
}
