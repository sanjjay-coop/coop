package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Document;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class DocumentService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private DocumentRepo documentRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Document> oe = this.documentRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addDocument(Document obj, String updateBy) throws IOException {

		obj.setFileName(StringUtils.cleanPath(obj.getFile().getOriginalFilename()));
		obj.setFileType(obj.getFile().getContentType());
		obj.setDocument(obj.getFile().getBytes());
		
		obj.setAddDefaults(updateBy);
		
		obj = documentRepo.save(obj);
		
		audit = new Audit(updateBy, "Document", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteDocument(Long id, String updateBy) {

		Optional<Document> oe = this.documentRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Document obj = oe.get();
		
		audit = new Audit(updateBy, "Document", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		documentRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateDocument(Document document, String updateBy) throws IOException {
		
		Optional<Document> oe = this.documentRepo.findById(document.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Document obj = oe.get();
		
		obj.setTitle(document.getTitle());
		
		if (!document.getFile().isEmpty()) {
			obj.setFileName(StringUtils.cleanPath(document.getFile().getOriginalFilename()));
			obj.setFileType(document.getFile().getContentType());
			obj.setDocument(document.getFile().getBytes());
		}
		
		obj.setUpdateDefaults(updateBy);
		
		obj = documentRepo.save(obj);
		
		audit = new Audit(updateBy, "Document", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
