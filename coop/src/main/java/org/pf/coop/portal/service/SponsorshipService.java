package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Sponsorship;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.SponsorshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SponsorshipService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SponsorshipRepo sponsorshipRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Sponsorship> oe = this.sponsorshipRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional 
	public Object getActiveById(Long id) {
		if (id == null) return null;
		
		Optional<Sponsorship> oe = this.sponsorshipRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		
		Sponsorship obj = oe.get();
		
		Date dt = Calendar.getInstance().getTime();
		
		if (obj.getLastDate().after(dt) && obj.getPubDate().before(dt) && obj.getExpDate().after(dt)) return obj;
		
		return null;
	}
	
	@Transactional
	public TransactionResult addSponsorship(Sponsorship obj, String updateBy) {
		
		obj = sponsorshipRepo.save(obj);
	
		audit = new Audit(updateBy, "Sponsorship", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSponsorship(Long id, String updateBy) {

		Optional<Sponsorship> oe = this.sponsorshipRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Sponsorship obj = oe.get();
		
		audit = new Audit(updateBy, "Sponsorship", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		sponsorshipRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSponsorship(Sponsorship sponsorship, String updateBy) {
		
		Optional<Sponsorship> oe = this.sponsorshipRepo.findById(sponsorship.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Sponsorship obj = oe.get();
				
		obj.setDescription(sponsorship.getDescription());
		obj.setExpDate(sponsorship.getExpDate());
		obj.setLastDate(sponsorship.getLastDate());
		obj.setPubDate(sponsorship.getPubDate());
		obj.setTitle(sponsorship.getTitle());
		
		obj = sponsorshipRepo.save(obj);
		
		audit = new Audit(updateBy, "Sponsorship", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

