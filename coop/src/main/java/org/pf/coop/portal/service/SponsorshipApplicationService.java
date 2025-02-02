package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.SponsorshipApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SponsorshipApplicationService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SponsorshipApplicationRepo sponsorshipApplicationRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<SponsorshipApplication> oe = this.sponsorshipApplicationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSponsorshipApplication(SponsorshipApplication obj, String updateBy) {
		
		obj.setApplicationDate(Calendar.getInstance().getTime());
		obj.setStatus("RECEIVED");
		obj = sponsorshipApplicationRepo.save(obj);
	
		audit = new Audit(updateBy, "SponsorshipApplication", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSponsorshipApplication(Long id, String updateBy) {

		Optional<SponsorshipApplication> oe = this.sponsorshipApplicationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SponsorshipApplication obj = oe.get();
		
		audit = new Audit(updateBy, "SponsorshipApplication", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		sponsorshipApplicationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSponsorshipApplication(SponsorshipApplication sponsorshipApplication, String updateBy) {
		
		Optional<SponsorshipApplication> oe = this.sponsorshipApplicationRepo.findById(sponsorshipApplication.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SponsorshipApplication obj = oe.get();
				
		obj.setStatus(sponsorshipApplication.getStatus());
		obj.setRemarks(sponsorshipApplication.getRemarks());
		obj.setStatusDate(sponsorshipApplication.getStatusDate());
		
		obj = sponsorshipApplicationRepo.save(obj);
		
		audit = new Audit(updateBy, "SponsorshipApplication", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}


