package org.pf.coop.portal.controller.member;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

import org.pf.coop.common.RandomString;
import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.IndexBaseController;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.repository.InvitationRepo;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.pf.coop.portal.service.InvitationService;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.validators.add.MemberAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/acceptInvitation")
public class AcceptInvitationController extends IndexBaseController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private InvitationRepo invitationRepo;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private GenderRepo genderRepo;

	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private SalutationRepo salutationRepo;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
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
	
	@GetMapping("/{id}/{random}")
	public String acceptInvitation(@PathVariable Long id, @PathVariable String random, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		if (principal != null) {
			reat.addFlashAttribute("message", "You're already logged in.");
			return "redirect:/";
		}
		
		try {
			
			Invitation invitation = this.invitationRepo.getReferenceById(id);
			
			if (invitation != null && !invitation.getRandom().equals(random)) {
				reat.addFlashAttribute("message", "Invitation does not exist.");
				return "redirect:/";
			}
			
			Member member = this.memberRepo.findByEmailIgnoreCase(invitation.getEmail());
			
			if (member!=null) {
				reat.addFlashAttribute("message", "Invitation is already accepted.");
				return "redirect:/";
			}
			
			member = new Member();
			member.setEmail(invitation.getEmail());
			member.setFirstName(invitation.getName());
			
			String in = invitation.getEmail();
			int iend = in.indexOf("@");
			
			if (iend != -1) {
				in = in.substring(0, iend);
			}
			
	        member.setMemId(in.replaceAll("[^a-zA-Z0-9]", ""));
			
			model.addAttribute("member", member);
			
			request.getSession().setAttribute("acceptInvitation_id", id);
			request.getSession().setAttribute("acceptInvitation_random", random);
			request.getSession().setAttribute("acceptInvitation_email", invitation.getEmail());
			
			return "member/acceptInvitation";
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/";
		}
	}
	
	@PostMapping("/*/*")
	public String memberAdd(@ModelAttribute Member member,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		if (principal != null) {
			reat.addFlashAttribute("message", "You're already logged in.");
			return "redirect:/";
		}
		
		Long id = (Long) request.getSession().getAttribute("acceptInvitation_id");
		String random = (String) request.getSession().getAttribute("acceptInvitation_random");
		
		Invitation invitation = this.invitationRepo.getReferenceById(id);
		
		if (invitation != null && !invitation.getRandom().equals(random) && !member.getEmail().equals(invitation.getEmail())) {
			reat.addFlashAttribute("message", "Invitation does not exist.");
			return "redirect:/";
		}
		
		member.setSubStartDate(Calendar.getInstance().getTime());
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 10);
		
		member.setSubEndDate(cal.getTime());
		
		this.memberAddValidator.validate(member, result);
		
		RandomString rb = new RandomString();
		member.setRetypePassword(rb.getAlphaNumericString(6));
		
		
		if (result.hasErrors()) {
			return "member/acceptInvitation";
		}
		
		Role role = this.roleRepo.findByCode("ROLE_MEMBER");
		
		try {
			TransactionResult tr = this.memberService.addMember(member, "");
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/";
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully. Please check your email for login details.");
				
				MessageText mt = messageTextRepo.findByMessageFor("WELCOME");
				
				this.emailService.sendEmail(member.getEmail(), mt.getSubject(), mt.getMessage(member));
				
				invitation.setStatus(true);
				
				this.invitationService.updateInvitation(invitation, "");
				
				if (role!=null) {
					member.setRoles(new TreeSet<Role>());
					member.getRoles().add(role);
					this.memberRepo.save(member);
				}
			} else {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/";
			}
			
			return "redirect:/";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "member/acceptInvitation";
		}
	}
}
