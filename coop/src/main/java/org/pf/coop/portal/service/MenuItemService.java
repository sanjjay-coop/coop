package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.MenuItem;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MenuItemService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MenuItemRepo menuItemRepo;
	
	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<MenuItem> oe = this.menuItemRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMenuItem(MenuItem obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = menuItemRepo.save(obj);
	
		audit = new Audit(updateBy, "MenuItem", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMenuItem(Long id, String updateBy) {

		Optional<MenuItem> oe = this.menuItemRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		MenuItem obj = oe.get();
		
		audit = new Audit(updateBy, "MenuItem", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		menuItemRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMenuItem(MenuItem menuItem, String updateBy) {
		
		Optional<MenuItem> oe = this.menuItemRepo.findById(menuItem.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		MenuItem obj = oe.get();
				
		obj.setCategory(menuItem.getCategory());
		obj.setItemType(menuItem.getItemType());
		obj.setNewPage(menuItem.getNewPage());
		obj.setTitle(menuItem.getTitle());
		obj.setUrl(menuItem.getUrl());
		obj.setNewPage(menuItem.getNewPage());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = menuItemRepo.save(obj);
		
		audit = new Audit(updateBy, "MenuItem", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
