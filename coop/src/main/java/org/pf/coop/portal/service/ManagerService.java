package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.TreeSet;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ManagerService {
	
	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private SalutationRepo salutationRepo;

	@Transactional
	public TransactionResult initiate() {

		Role role = new Role();
		
		role.setCode("ROLE_MANAGER");
		role.setDescription("Manager Role");
		
		role = this.roleRepo.save(role);
		
		audit = new Audit("system", "Role", role.toString(), role.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		EducationLevel el = new EducationLevel();
		
		el.setName("Graduate");
		
		el = this.educationLevelRepo.save(el);
		
		audit = new Audit("system", "EducationLevel", el.toString(), el.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		Gender gender = new Gender();
		
		gender.setName("Male");
		
		gender = this.genderRepo.save(gender);
		
		audit = new Audit("system", "Gender", gender.toString(), gender.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		MemberType memberType = new MemberType();
		
		memberType.setName("Ordinary");
		
		memberType = this.memberTypeRepo.save(memberType);
		
		audit = new Audit("system", "MemberType", memberType.toString(), memberType.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		Salutation salutation = new Salutation();
		
		salutation.setName("Mr.");
		
		salutation = this.salutationRepo.save(salutation);
		
		audit = new Audit("system", "Salutation", salutation.toString(), salutation.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		Member member = new Member();
		
		member.setSalutation(salutation);
		member.setFirstName("System");
		member.setLastName("Administrator");
		member.setMemId("admin");
		member.setMemType(memberType);
		member.setGender(gender);
		member.setEducationLevel(el);
		member.setMobile("1234567890");
		member.setEmail("abc@xyz.com");
		member.setProfilePublic(Boolean.FALSE);
		member.setSubStartDate(Calendar.getInstance().getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 10);
		member.setSubEndDate(cal.getTime());
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		member.setPassword(passwordEncoder.encode("admin"));
		
		member.setRoles(new TreeSet<Role>());
		member.getRoles().add(role);
		
		member = this.memberRepo.save(member);
		
		audit = new Audit("system", "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(true, "Record updated successfully");
	}	
}
