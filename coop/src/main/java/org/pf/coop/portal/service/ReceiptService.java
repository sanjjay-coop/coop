package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.ReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class ReceiptService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ReceiptRepo receiptRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Receipt> oe = this.receiptRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addReceipt(Receipt obj, String updateBy) throws IOException {

		obj.setFileName(StringUtils.cleanPath(obj.getFile().getOriginalFilename()));
		obj.setFileType(obj.getFile().getContentType());
		obj.setDocument(obj.getFile().getBytes());
		
		obj.setAddDefaults(updateBy);
		
		obj = receiptRepo.save(obj);
		
		audit = new Audit(updateBy, "Receipt", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteReceipt(Long id, String updateBy) {

		Optional<Receipt> oe = this.receiptRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Receipt obj = oe.get();
		
		audit = new Audit(updateBy, "Receipt", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		receiptRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateReceipt(Receipt receipt, String updateBy) throws IOException {
		
		Optional<Receipt> oe = this.receiptRepo.findById(receipt.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Receipt obj = oe.get();
		
		obj.setAmount(receipt.getAmount());
		obj.setDetails(receipt.getDetails());
		obj.setMember(receipt.getMember());
		obj.setReceiptDate(receipt.getReceiptDate());
		obj.setReceiptNumber(receipt.getReceiptNumber());
		
		obj.setFileName(StringUtils.cleanPath(receipt.getFile().getOriginalFilename()));
		obj.setFileType(receipt.getFile().getContentType());
		obj.setDocument(receipt.getFile().getBytes());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = receiptRepo.save(obj);
		
		audit = new Audit(updateBy, "Receipt", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

