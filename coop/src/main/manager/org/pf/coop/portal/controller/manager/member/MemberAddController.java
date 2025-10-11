package org.pf.coop.portal.controller.manager.member;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.RandomString;
import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.manager.ManagerBaseController;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.repository.CasteRepo;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.pf.coop.portal.repository.TribeRepo;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.validators.add.MemberAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/member/addNew")
public class MemberAddController extends ManagerBaseController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private CasteRepo casteRepo;

	@Autowired
	private TribeRepo tribeRepo;

	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private SalutationRepo salutationRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	
	@Autowired
	private OccupationRepo occupationRepo;
	
	@Autowired
	private MemberAddValidator memberAddValidator;
	
	@ModelAttribute("listEducationLevel")
	public List<EducationLevel> getListEducationLevel(){
		return (List<EducationLevel>) this.educationLevelRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listGender")
	public List<Gender> getListGender(){
		return (List<Gender>) this.genderRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listCaste")
	public List<Caste> getListCaste(){
		return (List<Caste>) this.casteRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listTribe")
	public List<Tribe> getListTribe(){
		return (List<Tribe>) this.tribeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listMaritalStatus")
	public List<MaritalStatus> getListMaritalStatus(){
		return (List<MaritalStatus>) this.maritalStatusRepo.findAll(Sort.by(Sort.Direction.ASC, "status"));
	}
	
	@ModelAttribute("listOccupation")
	public List<Occupation> getListOccupation(){
		return (List<Occupation>) this.occupationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listMemberGroup")
	public List<MemberGroup> getListMemberGroup(){
		return (List<MemberGroup>) this.memberGroupRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listMemberType")
	public List<MemberType> getListMemberType(){
		return (List<MemberType>) this.memberTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listRole")
	public List<Role> getListRole(){
		return (List<Role>) this.roleRepo.findAll(Sort.by(Sort.Direction.ASC, "code"));
	}
	
	@ModelAttribute("listSalutation")
	public List<Salutation> getListSalutation(){
		return (List<Salutation>) this.salutationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String memberAdd(Model model) {
		
		Member member = new Member();
		
		model.addAttribute(member);
		
		return "manager/member/addNew";
	}
	
	@PostMapping
	public String memberAdd(@ModelAttribute Member member,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.memberAddValidator.validate(member, result);
		
		RandomString rb = new RandomString();
		member.setRetypePassword(rb.getAlphaNumericString(6));
		
		if (result.hasErrors()) {
			return "manager/member/addNew";
		}
		
		try {
			TransactionResult tr = this.memberService.addMember(member, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/member/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
				
				MessageText mt = messageTextRepo.findByMessageFor("WELCOME");
				
				this.emailService.sendEmail(member.getEmail(), mt.getSubject(), mt.getMessage(member));
			}
			
			return "redirect:/manager/member/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/member/addNew";
		}
	}
}
