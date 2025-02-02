package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.MemberApplication;
import org.pf.coop.portal.repository.MemberApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberApplicationValidator extends BaseValidator implements Validator {

	@Autowired
	private MemberApplicationRepo memberApplicationRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return MemberApplication.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MemberApplication obj = (MemberApplication) target;
		
		if (obj.getSalutation()==null) {
			errors.rejectValue("salutation", "member.salutation.requied");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "member.firstName.required");
		
		if (obj.getFirstName()!=null) {
			if (!this.lengthRange(obj.getFirstName(), 1, 50)) {
				errors.rejectValue("firstName", "member.firstName.size");
			}
		}
		
		if (obj.getMiddleName()!=null) {
			if (!this.lengthRange(obj.getMiddleName(), 0, 50)) {
				errors.rejectValue("middleName", "member.middleName.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "member.lastName.required");
		
		if (obj.getLastName()!=null) {
			if (!this.lengthRange(obj.getLastName(), 1, 50)) {
				errors.rejectValue("lastName", "member.lastName.size");
			}
		}
		
		if (obj.getGender()==null) {
			errors.rejectValue("gender", "member.gender.required");
		}
		
		if (obj.getResAddress()!=null) {
			if (!this.lengthRange(obj.getResAddress(), 0, 100)) {
				errors.rejectValue("resAddress", "member.resAddress.size");
			}
		}

		if (obj.getResCity()!=null) {
			if (!this.lengthRange(obj.getResCity(), 0, 50)) {
				errors.rejectValue("resCity", "member.resCity.size");
			}
		}
		
		if (obj.getResPin()!=null) {
			if (!this.lengthRange(obj.getResPin(), 0, 10)) {
				errors.rejectValue("resPin", "member.resPin.size");
			}
		}

		if (obj.getResState()!=null) {
			if (!this.lengthRange(obj.getResState(), 0, 50)) {
				errors.rejectValue("resState", "member.resState.size");
			}
		}

		if (obj.getResCountry()!=null) {
			if (!this.lengthRange(obj.getResCountry(), 0, 50)) {
				errors.rejectValue("resCountry", "member.resCountry.size");
			}
		}		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "member.mobile.required");
		
		if (obj.getMobile()!=null) {
			if (!this.lengthRange(obj.getMobile(), 1, 20)) {
				errors.rejectValue("mobile", "member.mobile.size");
			}
			
			MemberApplication o = this.memberApplicationRepo.findByMobile(obj.getMobile());
			if (o!=null) {
				errors.rejectValue("mobile", "member.mobile.unique");
			}
			
			if (!this.isNumeric(obj.getMobile())) {
				errors.rejectValue("mobile", "member.mobile.format");
			}
		}
		
		if (obj.getOffDesignation()!=null) {
			if (!this.lengthRange(obj.getOffDesignation(), 0, 50)) {
				errors.rejectValue("offDesignation", "member.offDesignation.size");
			}
		}
		
		if (obj.getOffName()!=null) {
			if (!this.lengthRange(obj.getOffName(), 0, 200)) {
				errors.rejectValue("offName", "member.offName.size");
			}
		}
		
		if (obj.getOffAddress()!=null) {
			if (!this.lengthRange(obj.getOffAddress(), 0, 100)) {
				errors.rejectValue("offAddress", "member.offAddress.size");
			}
		}

		if (obj.getOffCity()!=null) {
			if (!this.lengthRange(obj.getOffCity(), 0, 50)) {
				errors.rejectValue("offCity", "member.offCity.size");
			}
		}
		
		if (obj.getOffPin()!=null) {
			if (!this.lengthRange(obj.getOffPin(), 0, 10)) {
				errors.rejectValue("offPin", "member.offPin.size");
			}
		}

		if (obj.getOffState()!=null) {
			if (!this.lengthRange(obj.getOffState(), 0, 50)) {
				errors.rejectValue("offState", "member.offState.size");
			}
		}

		if (obj.getOffCountry()!=null) {
			if (!this.lengthRange(obj.getOffCountry(), 0, 50)) {
				errors.rejectValue("offCountry", "member.offCountry.size");
			}
		}		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "member.email.required");
		
		if (obj.getEmail()!=null) {
			if (!this.lengthRange(obj.getEmail(), 1, 100)) {
				errors.rejectValue("email", "member.email.size");
			}
			
			MemberApplication o = this.memberApplicationRepo.findByEmailIgnoreCase(obj.getEmail());
			if (o!=null) {
				errors.rejectValue("email", "member.email.unique");
			}
			
			if (!this.isEmail(obj.getEmail())) {
				errors.rejectValue("email", "member.email.format");
			}
		}
		
		if (obj.getAadhaar()!=null) {
			if (!this.lengthRange(obj.getAadhaar(), 0, 20)) {
				errors.rejectValue("aadhaar", "member.aadhaar.size");
			}
		}
	}
}
