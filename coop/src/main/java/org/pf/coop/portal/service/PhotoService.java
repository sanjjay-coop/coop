package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Photo;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PhotoService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private PhotoRepo photoRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Photo> oe = this.photoRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addPhoto(Photo obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = photoRepo.save(obj);
		
		audit = new Audit(updateBy, "Photo", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deletePhoto(Long id, String updateBy) {

		Optional<Photo> oe = this.photoRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Photo obj = oe.get();
		
		audit = new Audit(updateBy, "Photo", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		photoRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updatePhoto(Photo photo, String updateBy) {
		
		Optional<Photo> oe = this.photoRepo.findById(photo.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Photo obj = oe.get();
		
		obj.setUpdateDefaults(updateBy);
		
		obj = photoRepo.save(obj);
		
		audit = new Audit(updateBy, "Photo", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
