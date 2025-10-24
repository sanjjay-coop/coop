package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Gallery;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class GalleryService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private GalleryRepo galleryRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Gallery> oe = this.galleryRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addGallery(Gallery obj, String updateBy) throws IOException {
		
		obj.setFileName(StringUtils.cleanPath(obj.getFile().getOriginalFilename()));
		obj.setFileType(obj.getFile().getContentType());
		obj.setFileData(obj.getFile().getBytes());
		
		obj.setAddDefaults(updateBy);
		
		obj = galleryRepo.save(obj);
		
		audit = new Audit(updateBy, "Gallery", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteGallery(Long id, String updateBy) {

		Optional<Gallery> oe = this.galleryRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Gallery obj = oe.get();
		
		audit = new Audit(updateBy, "Gallery", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		galleryRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateGallery(Gallery gallery, String updateBy) throws IOException {
		
		Optional<Gallery> oe = this.galleryRepo.findById(gallery.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Gallery obj = oe.get();
		
		if (!gallery.getFile().isEmpty()) {
			obj.setFileName(StringUtils.cleanPath(gallery.getFile().getOriginalFilename()));
			obj.setFileType(gallery.getFile().getContentType());
			obj.setFileData(gallery.getFile().getBytes());
		}
		
		obj.setDescription(gallery.getDescription());
		obj.setTitle(gallery.getTitle());
		obj.setDate(gallery.getDate());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = galleryRepo.save(obj);
		
		audit = new Audit(updateBy, "Gallery", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
