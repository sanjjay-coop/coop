package org.pf.coop.portal.controller.mobile;

import java.util.List;

import org.pf.coop.common.RandomString;
import org.pf.coop.common.TransactionResult;
import org.pf.coop.forms.OtpForm;
import org.pf.coop.portal.email.EmailService;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Quotation;
import org.pf.coop.portal.repository.MemberRepo;
import org.pf.coop.portal.repository.QuotationRepo;
import org.pf.coop.portal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mobile/loginOtp")
public class MobileLoginWithOtpController extends MobileBaseController {

	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	QuotationRepo quotationRepo;
	
	@Autowired
	EmailService emailService;
	
	@ModelAttribute("loginQuotation")
	public Quotation getLoginQuotation(){
		List<Quotation> listQuotation = this.quotationRepo.findRandomQuotation();
		
		if (listQuotation.isEmpty()) return null;
		return listQuotation.get(0);
	}
	
	@GetMapping
	public String otpForm(Model model) {
		
		OtpForm otpForm = new  OtpForm();
		
		model.addAttribute("otpForm", otpForm);
		
		return "mobile/otpForm";
	}
	
	@PostMapping
	public String otpForm(@ModelAttribute OtpForm otpForm,
			BindingResult result, Model model, RedirectAttributes reat) {
		
		if (otpForm.getEmailMobile()==null) {
			reat.addFlashAttribute("message", "Not valid Email Id.");
			return "redirect:/mobile/loginOtp";
		} else {
			if (otpForm.getEmailMobile().length()<4) {
				reat.addFlashAttribute("message", "Not valid Email Id.");
				return "redirect:/mobile/loginOtp";
			}
		}
		
		Member member = this.memberRepo.findByEmailIgnoreCase(otpForm.getEmailMobile());
		
		model.addAttribute("otpForm", otpForm);
		
		if (member != null) {
			RandomString rm = new RandomString();
			String otp = rm.getAlphaNumericString(6);
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			member.setOtp(encoder.encode(otp));
			
			System.out.println("OTP: " + otp);
			
			try {
				TransactionResult tr = this.memberService.saveOtp(member, "system");
				
				if (tr != null) {
					
					if (tr.isStatus()) {
						
						this.emailService.sendEmailOtp(member.getEmail(), otp);
					}
					
				} else {
					
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		
		return "mobile/otpFormComplete";
	}
}
