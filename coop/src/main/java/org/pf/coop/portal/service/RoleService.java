package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	private Audit audit;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Role> oe = this.roleRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addRole(Role obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = roleRepo.save(obj);
	
		audit = new Audit(updateBy, "Role", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteRole(Long id, String updateBy) {

		Optional<Role> oe = this.roleRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Role obj = oe.get();
		
		audit = new Audit(updateBy, "Role", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		roleRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateRole(Role role, String updateBy) {
		
		Optional<Role> oe = this.roleRepo.findById(role.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Role obj = oe.get();
		
		obj.setCode(role.getCode());
		obj.setDescription(role.getDescription());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = roleRepo.save(obj);
		
		audit = new Audit(updateBy, "Role", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

