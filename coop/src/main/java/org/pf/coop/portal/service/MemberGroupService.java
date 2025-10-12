package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MemberGroupService {

	@Autowired
	private AuditRepo auditRepo;
	
	
	private Audit audit;
	
	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberRepo memberRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<MemberGroup> oe = this.memberGroupRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMemberGroup(MemberGroup obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = memberGroupRepo.save(obj);
		
		audit = new Audit(updateBy, "MemberGroup", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMemberGroup(Long id, String updateBy) {

		Optional<MemberGroup> oe = this.memberGroupRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		MemberGroup obj = oe.get();
		
		if (!obj.getChildren().isEmpty()) return new TransactionResult(false, "Selected Group has Children associated with it. Cannot delete.");
		
		audit = new Audit(updateBy, "MemberGroup", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		Optional<Member> oeMember = this.memberRepo.findByMemGroup(obj);
		
		if (!oeMember.isEmpty()) {
			return new TransactionResult(false, "Group is assigned to Member. Cannot delete.");
		}
		
		memberGroupRepo.deleteMemberGroup(obj.getId());

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMemberGroup(MemberGroup memberGroup, String updateBy) {
		
		Optional<MemberGroup> oe = this.memberGroupRepo.findById(memberGroup.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		MemberGroup obj = oe.get();
		
		obj.setName(memberGroup.getName());
		obj.setParentGroup(memberGroup.getParentGroup());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = memberGroupRepo.save(obj);
		
		audit = new Audit(updateBy, "MemberGroup", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
