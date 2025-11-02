package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.OrderCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class OrderCategoryService {
	
	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;

	@Autowired
	private OrderCategoryRepo orderCategoryRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<OrderCategory> oe = this.orderCategoryRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addOrderCategory(OrderCategory obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = orderCategoryRepo.save(obj);
		
		audit = new Audit(updateBy, "OrderCategory", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteOrderCategory(Long id, String updateBy) {

		Optional<OrderCategory> oe = this.orderCategoryRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		OrderCategory obj = oe.get();
		
		audit = new Audit(updateBy, "OrderCategory", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		orderCategoryRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateOrderCategory(OrderCategory orderCategory, String updateBy) {
		
		Optional<OrderCategory> oe = this.orderCategoryRepo.findById(orderCategory.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		OrderCategory obj = oe.get();
		
		obj.setName(orderCategory.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = orderCategoryRepo.save(obj);
		
		audit = new Audit(updateBy, "OrderCategory", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
