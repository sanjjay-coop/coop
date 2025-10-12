package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.OccupationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class OccupationService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private OccupationRepo occupationRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Occupation> oe = this.occupationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addOccupation(Occupation obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = occupationRepo.save(obj);
	
		audit = new Audit(updateBy, "Occupation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteOccupation(Long id, String updateBy) {

		Optional<Occupation> oe = this.occupationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Occupation obj = oe.get();
		
		audit = new Audit(updateBy, "Occupation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		occupationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateOccupation(Occupation occupation, String updateBy) {
		
		Optional<Occupation> oe = this.occupationRepo.findById(occupation.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Occupation obj = oe.get();
				
		obj.setName(occupation.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = occupationRepo.save(obj);
		
		audit = new Audit(updateBy, "Occupation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
