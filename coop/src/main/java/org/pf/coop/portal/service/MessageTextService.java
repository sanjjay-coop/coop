package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MessageTextService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MessageTextRepo messageTextRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<MessageText> oe = this.messageTextRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMessageText(MessageText obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = messageTextRepo.save(obj);
	
		audit = new Audit(updateBy, "MessageText", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMessageText(Long id, String updateBy) {

		Optional<MessageText> oe = this.messageTextRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		MessageText obj = oe.get();
		
		audit = new Audit(updateBy, "MessageText", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		messageTextRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMessageText(MessageText messageText, String updateBy) {
		
		Optional<MessageText> oe = this.messageTextRepo.findById(messageText.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		MessageText obj = oe.get();
				
		obj.setMessageFor(messageText.getMessageFor());
		obj.setSubject(messageText.getSubject());
		obj.setContent(messageText.getContent());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = messageTextRepo.save(obj);
		
		audit = new Audit(updateBy, "MessageText", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
