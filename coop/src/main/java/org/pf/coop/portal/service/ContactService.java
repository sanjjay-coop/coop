package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Contact;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ContactService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ContactRepo contactRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Contact> oe = this.contactRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addContact(Contact obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = contactRepo.save(obj);
	
		audit = new Audit(updateBy, "Contact", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteContact(Long id, String updateBy) {

		Optional<Contact> oe = this.contactRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Contact obj = oe.get();
		
		audit = new Audit(updateBy, "Contact", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		contactRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateContact(Contact contact, String updateBy) {
		
		Optional<Contact> oe = this.contactRepo.findById(contact.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Contact obj = oe.get();
			
		obj.setAddress(contact.getAddress());
		obj.setDesignation(contact.getDesignation());
		obj.setEmail(contact.getEmail());
		obj.setFax(contact.getFax());
		obj.setName(contact.getName());
		obj.setPhone(contact.getPhone());
		obj.setAbout(contact.getAbout());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = contactRepo.save(obj);
		
		audit = new Audit(updateBy, "Contact", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

