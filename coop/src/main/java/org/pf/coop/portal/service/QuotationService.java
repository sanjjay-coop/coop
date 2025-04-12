package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class QuotationService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private QuotationRepo quotationRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Quotation> oe = this.quotationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addQuotation(Quotation obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = quotationRepo.save(obj);
	
		audit = new Audit(updateBy, "Quotation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteQuotation(Long id, String updateBy) {

		Optional<Quotation> oe = this.quotationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Quotation obj = oe.get();
		
		audit = new Audit(updateBy, "Quotation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		quotationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateQuotation(Quotation quotation, String updateBy) {
		
		Optional<Quotation> oe = this.quotationRepo.findById(quotation.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Quotation obj = oe.get();
				
		obj.setQuote(quotation.getQuote());
		obj.setAuthor(quotation.getAuthor());
		obj.setSource(quotation.getSource());
		
		obj.setUpdateDefaults(updateBy);
		obj = quotationRepo.save(obj);
		
		audit = new Audit(updateBy, "Quotation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<Quotation> listObj = this.quotationRepo.findBySearchString(null);
		
		for(Quotation obj : listObj) {
			obj.setUpdateDefaults("system");
			this.quotationRepo.save(obj);
		}
	}
}


