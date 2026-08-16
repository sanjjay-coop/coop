package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.forms.ChangePasswordForm;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class MemberService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MemberRepo memberRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Member> oe = this.memberRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMember(Member obj, String updateBy) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		obj.setPassword(passwordEncoder.encode(obj.getRetypePassword()));
		
		obj.setAddDefaults(updateBy);
		obj = memberRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMember(Long id, String updateBy) {

		Optional<Member> oe = this.memberRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Member obj = oe.get();
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		memberRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}	

	@Transactional
	public TransactionResult updateMember(Member member, String updateBy) {
		
		Optional<Member> oe = this.memberRepo.findById(member.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Member obj = oe.get();
		
		obj.setAadhaar(member.getAadhaar());
		obj.setDateOfBirth(member.getDateOfBirth());
		obj.setEducation(member.getEducation());
		obj.setEducationLevel(member.getEducationLevel());
		obj.setEmail(member.getEmail());
		obj.setExperience(member.getExperience());
		obj.setFirstName(member.getFirstName());
		obj.setGender(member.getGender());
		obj.setCaste(member.getCaste());
		obj.setTribe(member.getTribe());
		obj.setLastName(member.getLastName());
		obj.setMemId(member.getMemId());
		obj.setMemGroup(member.getMemGroup());
		obj.setMemType(member.getMemType());
		obj.setMiddleName(member.getMiddleName());
		obj.setMobile(member.getMobile());
		obj.setOffAddress(member.getOffAddress());
		obj.setOffCity(member.getOffCity());
		obj.setOffCountry(member.getOffCountry());
		obj.setOffDesignation(member.getOffDesignation());
		obj.setOffName(member.getOffName());
		obj.setOffPin(member.getOffPin());
		obj.setOffState(member.getOffState());
		obj.setProfilePublic(member.getProfilePublic());
		obj.setResAddress(member.getResAddress());
		obj.setResCity(member.getResCity());
		obj.setResCountry(member.getResCountry());
		obj.setResPin(member.getResPin());
		obj.setResState(member.getResState());
		obj.setSalutation(member.getSalutation());
		obj.setSubEndDate(member.getSubEndDate());
		obj.setSubStartDate(member.getSubStartDate());
		obj.setRoles(member.getRoles());	
		obj.setMaritalStatus(member.getMaritalStatus());
		obj.setTheme(member.getTheme());
		
		obj.setUpdateDefaults(updateBy);
		obj = memberRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}	
	
	@Transactional
	public TransactionResult saveOtp(Member member, String updateBy) {
		
		Optional<Member> oe = this.memberRepo.findById(member.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Member obj = oe.get();
		
		obj.setOtp(member.getOtp());
		obj.setOtpDate(Calendar.getInstance().getTime());
		
		obj.setUpdateDefaults(updateBy);
		obj = memberRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}	

	@Transactional
	public TransactionResult updateMemberSelf(Member member, String updateBy) {
		
		Optional<Member> oe = this.memberRepo.findById(member.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Member obj = oe.get();
		
		obj.setAadhaar(member.getAadhaar());
		obj.setGender(member.getGender());
		obj.setMaritalStatus(member.getMaritalStatus());
		obj.setOccupation(member.getOccupation());
		obj.setMemGroup(member.getMemGroup());
		obj.setCaste(member.getCaste());
		obj.setTribe(member.getTribe());
		obj.setEducationLevel(member.getEducationLevel());
		obj.setProfilePublic(member.getProfilePublic());
		obj.setDateOfBirth(member.getDateOfBirth());
		obj.setEducation(member.getEducation());
		obj.setExperience(member.getExperience());
		
		obj.setOffAddress(member.getOffAddress());
		obj.setOffCity(member.getOffCity());
		obj.setOffCountry(member.getOffCountry());
		obj.setOffPin(member.getOffPin());
		obj.setOffState(member.getOffState());
		
		obj.setOffDesignation(member.getOffDesignation());
		obj.setOffName(member.getOffName());
		
		obj.setResAddress(member.getResAddress());
		obj.setResCity(member.getResCity());
		obj.setResCountry(member.getResCountry());
		obj.setResPin(member.getResPin());
		obj.setResState(member.getResState());
		
		obj.setTheme(member.getTheme());
		
		obj.setUpdateDefaults(updateBy);
		obj = memberRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult updateProfilePhoto(MultipartFile file, Member o, String updateBy) throws IOException {
		
		Member obj = this.memberRepo.findByMemIdIgnoreCase(o.getMemId());
		
		if (obj==null) {return new TransactionResult(obj, false);}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		obj.setFileName(fileName);
		obj.setFileType(file.getContentType());
		obj.setFileData(file.getBytes());
		
		obj = memberRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPDPHOTO");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult changePassword(ChangePasswordForm cpf, String updateBy) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(updateBy);
		
		if (member == null) return new TransactionResult(member, false, "Record not found. Password not changed.");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (!passwordEncoder.matches(cpf.getCurrentPassword(), member.getPassword())) {
			return new TransactionResult(member, false, "Current password did not match. Password not changed.");
		}
		
		member.setPassword((new BCryptPasswordEncoder()).encode(cpf.getNewPassword()));
		
		member = memberRepo.save(member);
	
		audit = new Audit(updateBy, "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "CHGPASSWD");
		auditRepo.save(audit);
		
		return new TransactionResult(member, true);
	}
	
	@Transactional
	public TransactionResult resetPassword(Member obj) {
		
		Member member = this.memberRepo.findByMemIdIgnoreCase(obj.getMemId());
		
		if (member == null) return new TransactionResult(member, false, "Record not found. Password not changed.");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		member.setPassword(passwordEncoder.encode(obj.getPassword()));
		
		member = memberRepo.save(member);
		
		audit = new Audit("SELF", "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "RESETPASSWD");
		auditRepo.save(audit);
	
		return new TransactionResult(member, true);
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<Member> listObj = this.memberRepo.findBySearchString(null);
		
		for(Member obj : listObj) {
			obj.setUpdateDefaults("system");
			this.memberRepo.save(obj);
		}
	}
}

