package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MemberTypeService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<MemberType> oe = this.memberTypeRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMemberType(MemberType obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = memberTypeRepo.save(obj);
		
		audit = new Audit(updateBy, "MemberType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMemberType(Long id, String updateBy) {

		Optional<MemberType> oe = this.memberTypeRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		MemberType obj = oe.get();
		
		audit = new Audit(updateBy, "MemberType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		memberTypeRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMemberType(MemberType memberType, String updateBy) {
		
		Optional<MemberType> oe = this.memberTypeRepo.findById(memberType.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		MemberType obj = oe.get();
		
		obj.setName(memberType.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = memberTypeRepo.save(obj);
		
		audit = new Audit(updateBy, "MemberType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

