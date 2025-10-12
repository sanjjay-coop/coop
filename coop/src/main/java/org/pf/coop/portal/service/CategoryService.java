package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;

	@Autowired
	private CategoryRepo categoryRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Category> oe = this.categoryRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addCategory(Category obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = categoryRepo.save(obj);
		
		audit = new Audit(updateBy, "Category", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteCategory(Long id, String updateBy) {

		Optional<Category> oe = this.categoryRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Category obj = oe.get();
		
		audit = new Audit(updateBy, "Category", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		categoryRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateCategory(Category category, String updateBy) {
		
		Optional<Category> oe = this.categoryRepo.findById(category.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Category obj = oe.get();
		
		obj.setName(category.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = categoryRepo.save(obj);
		
		audit = new Audit(updateBy, "Category", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

