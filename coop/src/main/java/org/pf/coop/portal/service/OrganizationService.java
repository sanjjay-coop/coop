package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Organization;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private OrganizationRepo organizationRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Organization> oe = this.organizationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addOrganization(Organization obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = organizationRepo.save(obj);
	
		audit = new Audit(updateBy, "Organization", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteOrganization(Long id, String updateBy) {

		Optional<Organization> oe = this.organizationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Organization obj = oe.get();
		
		audit = new Audit(updateBy, "Organization", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		organizationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateOrganization(Organization organization, String updateBy) {
		
		Optional<Organization> oe = this.organizationRepo.findById(organization.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Organization obj = oe.get();
				
		obj.setAddress(organization.getAddress());
		obj.setOrganizationName(organization.getOrganizationName());
		obj.setCity(organization.getCity());
		obj.setContactEmail(organization.getContactEmail());
		obj.setContactName(organization.getContactName());
		obj.setContactPhone(organization.getContactPhone());
		obj.setCountry(organization.getCountry());
		obj.setDescription(organization.getDescription());
		obj.setPin(organization.getPin());
		obj.setState(organization.getState());
		obj.setUrl(organization.getUrl());
		
		obj.setUpdateDefaults(updateBy);
		obj = organizationRepo.save(obj);
		
		audit = new Audit(updateBy, "Organization", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult updateOrganizationPhoto(MultipartFile file, Organization o, String updateBy) throws IOException {
		
		Organization obj = (Organization) this.getById(o.getId());
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		obj.setFileName(fileName);
		obj.setFileType(file.getContentType());
		obj.setFileData(file.getBytes());
		
		obj = organizationRepo.save(obj);
		
		audit = new Audit(updateBy, "Organization", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPDPHOTO");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<Organization> listObj = this.organizationRepo.findBySearchString(null);
		
		for(Organization obj : listObj) {
			obj.setUpdateDefaults("system");
			this.organizationRepo.save(obj);
		}
	}
}
