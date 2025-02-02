package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.InvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class InvitationService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private InvitationRepo invitationRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Invitation> oe = this.invitationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addInvitation(Invitation obj, String updateBy) {
		
		obj.setRandom(Integer.toString((int)(Math.random() * (999999 - 111111)) + 111111));
		obj.setDate(Calendar.getInstance().getTime());
		obj.setUpdateDate(Calendar.getInstance().getTime());
		
		obj = invitationRepo.save(obj);
	
		audit = new Audit(updateBy, "Invitation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteInvitation(Long id, String updateBy) {

		Optional<Invitation> oe = this.invitationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Invitation obj = oe.get();
		
		audit = new Audit(updateBy, "Invitation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		invitationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateInvitation(Invitation invitation, String updateBy) {
		
		Optional<Invitation> oe = this.invitationRepo.findById(invitation.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Invitation obj = oe.get();
				
		obj.setEmail(invitation.getEmail());
		obj.setName(invitation.getName());
		obj.setMember(invitation.getMember());
		obj.setUpdateDate(Calendar.getInstance().getTime());
		
		obj = invitationRepo.save(obj);
		
		audit = new Audit(updateBy, "Invitation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

