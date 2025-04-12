package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Business;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.BusinessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
		
		obj.setAddDefaults(updateBy);
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
		
		obj.setUpdateDefaults(updateBy);
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
	
	@Transactional
	public TransactionResult updateBusinessPhoto(MultipartFile file, Business o, String updateBy) throws IOException {
		
		Business obj = (Business) this.getById(o.getId());
		
		if (obj==null || !obj.getOwner().getMemId().equals(updateBy)) {return new TransactionResult(obj, false);}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		obj.setFileName(fileName);
		obj.setFileType(file.getContentType());
		obj.setFileData(file.getBytes());
		
		obj = businessRepo.save(obj);
		
		audit = new Audit(updateBy, "Business", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPDPHOTO");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<Business> listObj = this.businessRepo.findBySearchString(null);
		
		for(Business obj : listObj) {
			obj.setUpdateDefaults("system");
			this.businessRepo.save(obj);
		}
	}
}

