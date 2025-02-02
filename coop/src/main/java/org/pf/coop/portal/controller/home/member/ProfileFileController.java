package org.pf.coop.portal.controller.home.member;

import java.security.Principal;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.forms.FileUploadForm;
import org.pf.coop.portal.controller.home.HomeBaseController;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.service.MemberService;
import org.pf.coop.portal.validators.ProfilePhotoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/home/profile/file")
public class ProfileFileController extends HomeBaseController {
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProfilePhotoValidator profilePhotoValidator;
	
	@GetMapping
	public String updateProfileFile(Model model, RedirectAttributes reat, Principal principal) {
		
		FileUploadForm fileUploadForm = new FileUploadForm();
		
		fileUploadForm.setDescription(principal.getName());
		
		model.addAttribute(fileUploadForm);
		
		return "home/member/photo";
	}
	
	@PostMapping
	public String uploadProfileFile(@ModelAttribute FileUploadForm fileUploadForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.profilePhotoValidator.validate(fileUploadForm, result);
		
		if (result.hasErrors()) {
			return "home/member/photo";
		}
		
		if (!fileUploadForm.getDescription().equals(principal.getName())) {
			reat.addFlashAttribute("message", "Profile photo updation failed. Please try again later.");
			return "redirect:/home";
		}
		
		Member member = memberRepo.findByMemIdIgnoreCase(principal.getName());
		
		if (member == null) {
			reat.addFlashAttribute("message", "Profile photo updation failed. Please try again later.");
			return "redirect:/home/profile";
		}
		
		try {
			TransactionResult tr = this.memberService.updateProfilePhoto(fileUploadForm.getFile(), member, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Profile photo updation failed. Please try again later.");
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Profile photo updated successfully.");
				return "redirect:/home/profile";
			} else {
				reat.addFlashAttribute("message", "Profile updation failed. Please try again later.");
			}
			
			return "redirect:/home/profile";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			System.out.println(e.getMessage());
			return "redirect:/home/profile";
		}
	}
	
	@GetMapping("/photo/{memId}")
	public ResponseEntity<byte[]> getFile(@PathVariable String memId) {
	    
		Member member = this.memberRepo.findByMemIdIgnoreCase(memId);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + member.getFileName() + "\"")
	        .body(member.getFileData());
	}
}