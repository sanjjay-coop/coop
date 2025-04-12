package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Holiday;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class HolidayService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private HolidayRepo holidayRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Holiday> oe = this.holidayRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addHoliday(Holiday obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = holidayRepo.save(obj);
	
		audit = new Audit(updateBy, "Holiday", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteHoliday(Long id, String updateBy) {

		Optional<Holiday> oe = this.holidayRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Holiday obj = oe.get();
		
		audit = new Audit(updateBy, "Holiday", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		holidayRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<Holiday> listObj = this.holidayRepo.findBySearchString(null);
		
		for(Holiday obj : listObj) {
			obj.setUpdateDefaults("system");
			this.holidayRepo.save(obj);
		}
	}
}
