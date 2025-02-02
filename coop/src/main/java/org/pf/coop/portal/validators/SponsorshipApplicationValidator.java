package org.pf.coop.portal.validators;

import java.util.Calendar;
import java.util.Date;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.SponsorshipApplication;
import org.pf.coop.portal.repository.SponsorshipApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SponsorshipApplicationValidator extends BaseValidator implements Validator {
	
	@Autowired
	private SponsorshipApplicationRepo saRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return SponsorshipApplication.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SponsorshipApplication obj = (SponsorshipApplication) target;
		
		if (obj.getSponsorship()==null) {
			errors.rejectValue("sponsorship", "sponsorshipApplication.sponsorship.required");
		}
		else {
			Date dt = Calendar.getInstance().getTime();
			if (!(obj.getSponsorship().getLastDate().after(dt) &&
					obj.getSponsorship().getPubDate().before(dt) &&
					obj.getSponsorship().getExpDate().after(dt))) {
				errors.rejectValue("sponsorship", "sponsorshipApplication.sponsorship.valid");
			}
			
			if (obj.getEmail()!=null) {
				if (!(this.saRepo.findBySponsorshipAndEmail(obj.getSponsorship(), obj.getEmail()).isEmpty())) {
					errors.rejectValue("email", "sponsorshipApplication.email.unique");
				}
			}
			
			if (obj.getMobile()!=null) {
				if (!(this.saRepo.findBySponsorshipAndMobile(obj.getSponsorship(), obj.getMobile()).isEmpty())) {
					errors.rejectValue("mobile", "sponsorshipApplication.mobile.unique");
				}
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reasonsForApplication", 
				"sponsorshipApplication.reasonsForApplication.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "educationAndExperience", 
				"sponsorshipApplication.educationAndExperience.required");
		
		if (obj.getReasonsForApplication()!=null){
			if (!this.lengthRange(obj.getReasonsForApplication(), 1, 300)){
				errors.rejectValue("reasonsForApplication", "sponsorshipApplication.reasonsForApplication.size");
			}
		}

		if (obj.getEducationAndExperience()!=null){
			if (!this.lengthRange(obj.getEducationAndExperience(), 1, 500)){
				errors.rejectValue("educationAndExperience", "sponsorshipApplication.educationAndExperience.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", 
				"sponsorshipApplication.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "sponsorshipApplication.name.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", 
				"sponsorshipApplication.city.required");
		
		if (obj.getCity()!=null){
			if (!this.lengthRange(obj.getCity(), 1, 50)){
				errors.rejectValue("city", "sponsorshipApplication.city.size");
			}
		}
		
		if (obj.getPin()!=null){
			if (!this.lengthRange(obj.getPin(), 0, 10)){
				errors.rejectValue("pin", "sponsorshipApplication.pin.size");
			} else {
				if (!this.isNumeric(obj.getPin())) {
					errors.rejectValue("pin", "sponsorshipApplication.pin.format");
				}
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", 
				"sponsorshipApplication.state.required");
		
		if (obj.getState()!=null){
			if (!this.lengthRange(obj.getState(), 1, 50)){
				errors.rejectValue("state", "sponsorshipApplication.state.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", 
				"sponsorshipApplication.country.required");
		
		if (obj.getCountry()!=null){
			if (!this.lengthRange(obj.getCountry(), 1, 50)){
				errors.rejectValue("country", "sponsorshipApplication.country.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", 
				"sponsorshipApplication.email.required");
		
		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 1, 50)){
				errors.rejectValue("email", "sponsorshipApplication.email.size");
			} else {
				if (!this.isEmail(obj.getEmail())) {
					errors.rejectValue("email", "sponsorshipApplication.email.format");
				}
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", 
				"sponsorshipApplication.mobile.required");
		
		if (obj.getMobile()!=null){
			if (!this.lengthRange(obj.getMobile(), 1, 10)){
				errors.rejectValue("mobile", "sponsorshipApplication.mobile.size");
			} else {
				if (!this.isNumeric(obj.getMobile())) {
					errors.rejectValue("mobile", "sponsorshipApplication.mobile.format");
				}
			}
		}

	}
}
