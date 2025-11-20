package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.ServiceProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ServiceProviderService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ServiceProviderRepo serviceProviderRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<ServiceProvider> oe = this.serviceProviderRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addServiceProvider(ServiceProvider obj, String updateBy) {
		
		obj.setEnabled(false);
		
		obj.setAddDefaults(updateBy);
		obj = serviceProviderRepo.save(obj);
	
		audit = new Audit(updateBy, "ServiceProvider", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteServiceProvider(Long id, String updateBy) {

		Optional<ServiceProvider> oe = this.serviceProviderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		ServiceProvider obj = oe.get();
		
		audit = new Audit(updateBy, "ServiceProvider", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		serviceProviderRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateServiceProvider(ServiceProvider serviceProvider, String updateBy) {
		
		Optional<ServiceProvider> oe = this.serviceProviderRepo.findById(serviceProvider.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		ServiceProvider obj = oe.get();
				
		obj.setAddress(serviceProvider.getAddress());
		obj.setServiceName(serviceProvider.getServiceName());
		obj.setCity(serviceProvider.getCity());
		obj.setContactEmail(serviceProvider.getContactEmail());
		obj.setContactName(serviceProvider.getContactName());
		obj.setContactPhone(serviceProvider.getContactPhone());
		obj.setCountry(serviceProvider.getCountry());
		obj.setDescription(serviceProvider.getDescription());
		obj.setKeywords(serviceProvider.getKeywords());
		obj.setOwner(serviceProvider.getOwner());
		obj.setPin(serviceProvider.getPin());
		obj.setState(serviceProvider.getState());
		
		obj.setUpdateDefaults(updateBy);
		obj = serviceProviderRepo.save(obj);
		
		audit = new Audit(updateBy, "ServiceProvider", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult enableDisableServiceProvider(Long id, Boolean status, String updateBy) {

		Optional<ServiceProvider> oe = this.serviceProviderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		ServiceProvider obj = oe.get();
		
		obj.setEnabled(status);
		
		audit = new Audit(updateBy, "ServiceProvider", status.toString(), obj.getId(), Calendar.getInstance().getTime(), "ENABLE/DISABLE");
		auditRepo.save(audit);
		
		serviceProviderRepo.save(obj);

		return new TransactionResult(true, "Record updated successfully");
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<ServiceProvider> listObj = this.serviceProviderRepo.findBySearchString(null);
		
		for(ServiceProvider obj : listObj) {
			obj.setUpdateDefaults("system");
			this.serviceProviderRepo.save(obj);
		}
	}
}
