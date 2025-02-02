package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Funding;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.FundingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FundingService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private FundingRepo fundingRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Funding> oe = this.fundingRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addFunding(Funding obj, String updateBy) {
		
		obj.setApplicationDate(Calendar.getInstance().getTime());
		obj.setStatus("RECEIVED");
		
		obj = fundingRepo.save(obj);
	
		audit = new Audit(updateBy, "Funding", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteFunding(Long id, String updateBy) {

		Optional<Funding> oe = this.fundingRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Funding obj = oe.get();
		
		audit = new Audit(updateBy, "Funding", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		fundingRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateFunding(Funding funding, String updateBy) {
		
		Optional<Funding> oe = this.fundingRepo.findById(funding.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Funding obj = oe.get();
				
		obj = fundingRepo.save(obj);
		
		audit = new Audit(updateBy, "Funding", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult updateStatus(Funding funding, String updateBy) {
		
		Optional<Funding> oe = this.fundingRepo.findById(funding.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Funding obj = oe.get();
				
		obj.setAmountSanctioned(funding.getAmountSanctioned());
		obj.setSanctionDate(funding.getSanctionDate());
		obj.setStatus(funding.getStatus());
		obj.setRemarks(funding.getRemarks());
		obj.setStatusDate(Calendar.getInstance().getTime());
		obj = fundingRepo.save(obj);
		
		audit = new Audit(updateBy, "Funding", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPDSTATUS");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
