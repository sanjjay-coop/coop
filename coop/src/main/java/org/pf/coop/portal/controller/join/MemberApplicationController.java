package org.pf.coop.portal.controller.join;

import java.security.Principal;
import java.util.List;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.controller.BaseController;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.pf.coop.portal.repository.GenderRepo;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.pf.coop.portal.repository.OccupationRepo;
import org.pf.coop.portal.repository.SalutationRepo;
import org.pf.coop.portal.service.MemberApplicationService;
import org.pf.coop.portal.validators.MemberApplicationValidator;
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
@RequestMapping("/join")
public class MemberApplicationController extends BaseController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Autowired
	private GenderRepo genderRepo;

	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Autowired
	private SalutationRepo salutationRepo;
	
	@Autowired
	private MemberApplicationService memberApplicationService;
	
	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	
	@Autowired
	private OccupationRepo occupationRepo;
	
	@Autowired
	private MemberApplicationValidator memberApplicationValidator;
	
	@ModelAttribute("listEducationLevel")
	public List<EducationLevel> getListEducationLevel(){
		return (List<EducationLevel>) this.educationLevelRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listGender")
	public List<Gender> getListGender(){
		return (List<Gender>) this.genderRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
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
	
	@ModelAttribute("listSalutation")
	public List<Salutation> getListSalutation(){
		return (List<Salutation>) this.salutationRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String memberApplicationAdd(Model model, RedirectAttributes reat, Principal principal) {
		
		if (principal !=null) {
			reat.addFlashAttribute("message", "Member has already signed-in.");
			return "redirect:/";
		}
		
		MemberApplication memberApplication = new MemberApplication();
		
		model.addAttribute(memberApplication);
		
		return "join/addNew";
	}
	
	@PostMapping
	public String memberApplicationAdd(@ModelAttribute MemberApplication memberApplication,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		if (principal !=null) {
			reat.addFlashAttribute("message", "Member has already signed-in.");
			return "redirect:/";
		}
		
		this.memberApplicationValidator.validate(memberApplication, result);
		
		if (result.hasErrors()) {
			return "join/addNew";
		}
		
		try {
			TransactionResult tr = this.memberApplicationService.addMemberApplication(memberApplication, "guest");
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "redirect:/join/fail";
			} else {
								
				MessageText mt = messageTextRepo.findByMessageFor("MEMBER APPLICATION");
				
				this.emailService.sendEmail(memberApplication.getEmail(), mt.getSubject(), mt.getMessage(memberApplication));
				
				reat.addFlashAttribute("message", "Application No.: " + memberApplication.getId() + " received successfully.");
				return "redirect:/join/success";
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not added. Please try again later. " + e.getMessage());
			return "redirect:/join/fail";
		}
	}
	
	@GetMapping("/success")
	public String applicationSuccessView(Model model, RedirectAttributes reat) {
		
		return "join/success";
	}
	
	@GetMapping("/fail")
	public String applicationFailView(Model model, RedirectAttributes reat) {
		
		return "join/fail";
	}
}
