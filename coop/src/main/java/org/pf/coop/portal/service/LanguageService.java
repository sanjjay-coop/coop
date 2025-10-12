package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Language;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.LanguageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LanguageService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private LanguageRepo languageRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Language> oe = this.languageRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addLanguage(Language obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = languageRepo.save(obj);
	
		audit = new Audit(updateBy, "Language", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteLanguage(Long id, String updateBy) {

		Optional<Language> oe = this.languageRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Language obj = oe.get();
		
		audit = new Audit(updateBy, "Language", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		languageRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateLanguage(Language language, String updateBy) {
		
		Optional<Language> oe = this.languageRepo.findById(language.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Language obj = oe.get();
				
		obj.setName(language.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = languageRepo.save(obj);
		
		audit = new Audit(updateBy, "Language", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
