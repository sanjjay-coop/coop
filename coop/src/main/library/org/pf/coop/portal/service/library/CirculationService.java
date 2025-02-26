package org.pf.coop.portal.service.library;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.library.CheckIn;
import org.pf.coop.portal.model.library.CheckOut;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.library.CheckInRepo;
import org.pf.coop.portal.repository.library.CheckOutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CirculationService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private CheckOutRepo checkOutRepo;
	
	@Autowired
	private CheckInRepo checkInRepo;
	
	private Audit audit;
	
	@Transactional 
	public Object getCheckOutById(Long id) {
		if (id == null) return null;
		
		Optional<CheckOut> oe = this.checkOutRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addCheckOut(CheckOut obj, String updateBy) {
		
		obj.setTransactionDate(Calendar.getInstance().getTime());
		obj.setIssuedBy(updateBy);
		
		obj = checkOutRepo.save(obj);
		
		audit = new Audit(updateBy, "CHECKOUT", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult addCheckIn(CheckOut checkOut, String updateBy) {
		
		CheckIn obj = new CheckIn();
		
		obj.setCheckInDate(Calendar.getInstance().getTime());
		obj.setCheckOutDate(checkOut.getTransactionDate());
		obj.setIssuedBy(checkOut.getIssuedBy());
		obj.setMemberId(checkOut.getMember().getMemId());
		obj.setReceivedBy(updateBy);
		obj.setTitle(checkOut.getTitle());
		
		obj = checkInRepo.save(obj);
		
		audit = new Audit(updateBy, "CHECKIN", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		checkOutRepo.delete(checkOut);

		return new TransactionResult(obj, true);
	}
}