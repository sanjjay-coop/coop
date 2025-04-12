package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.NewsFeed;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.NewsFeedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class NewsFeedService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private NewsFeedRepo newsFeedRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<NewsFeed> oe = this.newsFeedRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addNewsFeed(NewsFeed obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = newsFeedRepo.save(obj);
	
		audit = new Audit(updateBy, "NewsFeed", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteNewsFeed(Long id, String updateBy) {

		Optional<NewsFeed> oe = this.newsFeedRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		NewsFeed obj = oe.get();
		
		audit = new Audit(updateBy, "NewsFeed", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		newsFeedRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateNewsFeed(NewsFeed newsFeed, String updateBy) {
		
		Optional<NewsFeed> oe = this.newsFeedRepo.findById(newsFeed.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		NewsFeed obj = oe.get();
				
		obj.setTitle(newsFeed.getTitle());
		obj.setDescription(newsFeed.getDescription());
		obj.setUrl(newsFeed.getUrl());
		
		obj.setUpdateDefaults(updateBy);
		obj = newsFeedRepo.save(obj);
		
		audit = new Audit(updateBy, "NewsFeed", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

}
