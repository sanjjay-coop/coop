package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Order;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private OrderRepo orderRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Order> oe = this.orderRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addOrder(Order obj, String updateBy) throws IOException {

		obj.setFileName(StringUtils.cleanPath(obj.getFile().getOriginalFilename()));
		obj.setFileType(obj.getFile().getContentType());
		obj.setDocument(obj.getFile().getBytes());
		
		obj.setAddDefaults(updateBy);
		
		obj = orderRepo.save(obj);
		
		audit = new Audit(updateBy, "Order", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteOrder(Long id, String updateBy) {

		Optional<Order> oe = this.orderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Order obj = oe.get();
		
		audit = new Audit(updateBy, "Order", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		orderRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateOrder(Order order, String updateBy) throws IOException {
		
		Optional<Order> oe = this.orderRepo.findById(order.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Order obj = oe.get();
		
		obj.setSubject(order.getSubject());
		obj.setOrderDate(order.getOrderDate());
		obj.setOrderNumber(order.getOrderNumber());
		obj.setOrderCategory(order.getOrderCategory());
		
		if (!order.getFile().isEmpty()) {
			obj.setFileName(StringUtils.cleanPath(order.getFile().getOriginalFilename()));
			obj.setFileType(order.getFile().getContentType());
			obj.setDocument(order.getFile().getBytes());
		}
		
		obj.setUpdateDefaults(updateBy);
		
		obj = orderRepo.save(obj);
		
		audit = new Audit(updateBy, "Order", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

