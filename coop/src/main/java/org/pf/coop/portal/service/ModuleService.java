package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Module;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.repository.ModuleRepo;
import org.pf.coop.portal.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ModuleService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ModuleRepo moduleRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Module> oe = this.moduleRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addModule(Module obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = moduleRepo.save(obj);
		
		audit = new Audit(updateBy, "Module", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteModule(Long id, String updateBy) {

		Optional<Module> oe = this.moduleRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Module obj = oe.get();
		
		audit = new Audit(updateBy, "Module", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		moduleRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateModule(Module module, String updateBy) {
		
		Optional<Module> oe = this.moduleRepo.findById(module.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Module obj = oe.get();
		
		obj.setName(module.getName());
		obj.setEnabled(module.getEnabled());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = moduleRepo.save(obj);
		
		audit = new Audit(updateBy, "Module", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
