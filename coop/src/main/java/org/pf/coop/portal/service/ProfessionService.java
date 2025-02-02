package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Profession;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.ProfessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProfessionService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ProfessionRepo professionRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Profession> oe = this.professionRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addProfession(Profession obj, String updateBy) {
		
		obj = professionRepo.save(obj);
	
		audit = new Audit(updateBy, "Profession", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteProfession(Long id, String updateBy) {

		Optional<Profession> oe = this.professionRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Profession obj = oe.get();
		
		audit = new Audit(updateBy, "Profession", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		professionRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateProfession(Profession profession, String updateBy) {
		
		Optional<Profession> oe = this.professionRepo.findById(profession.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Profession obj = oe.get();
				
		obj.setName(profession.getName());
		
		obj = professionRepo.save(obj);
		
		audit = new Audit(updateBy, "Profession", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
