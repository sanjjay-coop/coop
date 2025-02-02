package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BusinessService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private BusinessRepo businessRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Business> oe = this.businessRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addBusiness(Business obj, String updateBy) {
		
		obj.setAddDate(Calendar.getInstance().getTime());
		obj.setEnabled(false);
		
		obj = businessRepo.save(obj);
	
		audit = new Audit(updateBy, "Business", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteBusiness(Long id, String updateBy) {

		Optional<Business> oe = this.businessRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Business obj = oe.get();
		
		audit = new Audit(updateBy, "Business", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		businessRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateBusiness(Business business, String updateBy) {
		
		Optional<Business> oe = this.businessRepo.findById(business.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Business obj = oe.get();
				
		obj.setAddress(business.getAddress());
		obj.setBusinessName(business.getBusinessName());
		obj.setCity(business.getCity());
		obj.setContactEmail(business.getContactEmail());
		obj.setContactName(business.getContactName());
		obj.setContactPhone(business.getContactPhone());
		obj.setCountry(business.getCountry());
		obj.setDescription(business.getDescription());
		obj.setKeywords(business.getKeywords());
		obj.setOwner(business.getOwner());
		obj.setPin(business.getPin());
		obj.setState(business.getState());
		obj.setUrl(business.getUrl());
		
		obj = businessRepo.save(obj);
		
		audit = new Audit(updateBy, "Business", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult enableDisableBusiness(Long id, Boolean status, String updateBy) {

		Optional<Business> oe = this.businessRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Business obj = oe.get();
		
		obj.setEnabled(status);
		
		audit = new Audit(updateBy, "Business", status.toString(), obj.getId(), Calendar.getInstance().getTime(), "ENABLE/DISABLE");
		auditRepo.save(audit);
		
		businessRepo.save(obj);

		return new TransactionResult(true, "Record updated successfully");
	}
}

