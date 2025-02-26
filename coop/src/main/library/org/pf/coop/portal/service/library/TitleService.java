package org.pf.coop.portal.service.library;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.library.TitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TitleService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private TitleRepo titleRepo;
	
	private Audit audit;
	

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Title> oe = this.titleRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addTitle(Title obj, String updateBy) {
		
		obj = titleRepo.save(obj);
		
		audit = new Audit(updateBy, "Title", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteTitle(Long id, String updateBy) {

		Optional<Title> oe = this.titleRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Title obj = oe.get();
		
		audit = new Audit(updateBy, "Title", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		titleRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateTitle(Title title, String updateBy) {
		
		Optional<Title> oe = this.titleRepo.findById(title.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Title obj = oe.get();
		
		obj.setAccessionNumber(title.getAccessionNumber());
		obj.setAuthors(title.getAuthors());
		obj.setLibrary(title.getLibrary());
		obj.setSummary(title.getSummary());
		obj.setTitleType(title.getTitleType());
		obj.setUniformTitle(title.getUniformTitle());
		
		obj = titleRepo.save(obj);
		
		audit = new Audit(updateBy, "Title", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}


