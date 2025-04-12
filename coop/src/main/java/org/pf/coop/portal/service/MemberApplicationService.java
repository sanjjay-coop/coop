package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.pf.coop.common.RandomString;
import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MemberApplicationRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MemberApplicationService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	private Audit audit;
	
	@Autowired
	private MemberApplicationRepo memberApplicationRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<MemberApplication> oe = this.memberApplicationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMemberApplication(MemberApplication obj, String updateBy) {
		
		obj.setStatus(false);
		obj.setStatusDate(Calendar.getInstance().getTime());
		
		obj.setAddDefaults(updateBy);
		obj = memberApplicationRepo.save(obj);
		
		audit = new Audit(updateBy, "MemberApplication", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMemberApplication(Long id, String updateBy) {

		Optional<MemberApplication> oe = this.memberApplicationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		MemberApplication obj = oe.get();
		
		audit = new Audit(updateBy, "MemberApplication", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		memberApplicationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}	

	@Transactional
	public TransactionResult updateMemberApplicationStatus(MemberApplication memberApplication, String updateBy) {
		
		Optional<MemberApplication> oe = this.memberApplicationRepo.findById(memberApplication.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		MemberApplication obj = oe.get();
		
		obj.setStatus(memberApplication.getStatus());
		obj.setStatusDate(memberApplication.getStatusDate());	
		obj = memberApplicationRepo.save(obj);
		
		audit = new Audit(updateBy, "MemberApplication", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}	
	
	@Transactional
	public TransactionResult confirmMemberApplication(MemberApplication obj, String updateBy) {
		
		Member member = new Member();
		
		String in = obj.getEmail();
		int iend = in.indexOf("@");
		
		if (iend != -1) {
			in = in.substring(0, iend);
		}
		
        member.setMemId(in.replaceAll("[^a-zA-Z0-9]", ""));
        
        if (this.memberRepo.findByEmailIgnoreCase(obj.getEmail()) != null) return new TransactionResult(false, "Email must be unique.");
        if (this.memberRepo.findByMemIdIgnoreCase(member.getMemId()) != null) return new TransactionResult(false, "Member Id must be unique.");
        if (this.memberRepo.findByMobile(obj.getMobile()) != null) return new TransactionResult(false, "Mobile Number must be unique.");
        		
		member.setAadhaar(obj.getAadhaar());
		member.setDateOfBirth(obj.getDateOfBirth());
		member.setEducation(obj.getEducation());
		member.setEducationLevel(obj.getEducationLevel());
		member.setEmail(obj.getEmail());
		member.setExperience(obj.getExperience());
		member.setFirstName(obj.getFirstName());
		member.setGender(obj.getGender());
		member.setLastName(obj.getLastName());
		member.setMaritalStatus(obj.getMaritalStatus());
		member.setMemGroup(obj.getMemGroup());
		member.setMiddleName(obj.getMiddleName());
		member.setMobile(obj.getMobile());
		member.setOccupation(obj.getOccupation());
		member.setOffAddress(obj.getOffAddress());
		member.setOffCity(obj.getOffCity());
		member.setOffCountry(obj.getOffCountry());
		member.setOffDesignation(obj.getOffDesignation());
		member.setOffName(obj.getOffName());
		member.setOffPin(obj.getOffPin());
		member.setOffState(obj.getOffState());
		member.setResAddress(obj.getResAddress());
		member.setResCity(obj.getResCity());
		member.setResCountry(obj.getResCountry());
		member.setResPin(obj.getResPin());
		member.setResState(obj.getResState());
		member.setSalutation(obj.getSalutation());
		
		RandomString rb = new RandomString();
		
		member.setRetypePassword(rb.getAlphaNumericString(6));
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		member.setPassword(passwordEncoder.encode(member.getRetypePassword()));
		
		member.setRoles(new TreeSet<Role>());
		member.getRoles().add(this.roleRepo.findByCode("ROLE_MEMBER"));
		
		member.setMemType(this.memberTypeRepo.findByName("Ordinary"));
		
		member.setProfilePublic(false);
		
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.YEAR, 10);
		
		member.setSubStartDate(cal.getTime());
		member.setSubEndDate(cal1.getTime());
		
		member.setAddDefaults(updateBy);
		
		this.memberRepo.save(member);
		
		obj.setStatus(true);
		obj.setStatusDate(Calendar.getInstance().getTime());
	
		this.memberApplicationRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(member, true);
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<MemberApplication> listObj = this.memberApplicationRepo.findBySearchString(null);
		
		for(MemberApplication obj : listObj) {
			obj.setUpdateDefaults("system");
			this.memberApplicationRepo.save(obj);
		}
	}
}

