package org.pf.coop.portal.controller.home.member;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.repository.RoleRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.validators.edit.MemberSelfEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberProfileController extends HomeBaseController {
	
	@Autowired
	private MemberRepo memberRepo;

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
	private MaritalStatusRepo maritalStatusRepo;
	
	@Autowired
	private OccupationRepo occupationRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberSelfEditValidator memberSelfEditValidator;
	
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
	
	@ModelAttribute("listMaritalStatus")
	public List<MaritalStatus> getListMaritalStatus(){
		return (List<MaritalStatus>) this.maritalStatusRepo.findAll(Sort.by(Sort.Direction.ASC, "status"));
	}
	
	@ModelAttribute("listOccupation")
	public List<Occupation> getListOccupation(){
		return (List<Occupation>) this.occupationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/home/profile")
	public String memberProfile(Model model, RedirectAttributes reat, Principal principal) {

		try {
			Member member= this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home";
			}
	
			model.addAttribute("member", member);
			
			return "home/member/profile";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home";
		}
	}
	
	@GetMapping("/home/profile/edit")
	public String memberProfileEdit(Model model, RedirectAttributes reat, Principal principal) {

		try {
			Member member= this.memberRepo.findByMemIdIgnoreCase(principal.getName());
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home";
			}
	
			model.addAttribute("member", member);
			
			return "home/member/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home";
		}
	}
	
	@PostMapping("/home/profile/edit")
	public String memberProfileEdit(@ModelAttribute Member member,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.memberSelfEditValidator.validate(member, result);
		
		if (result.hasErrors()) {
			return "home/member/edit";
		}
		
		try {
			TransactionResult tr = this.memberService.updateMemberSelf(member, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/home/profile";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/home/profile/edit";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/home/profile/edit";
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/home/profile/edit";
		}
	}
}
