package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberEditValidator extends BaseValidator implements Validator {

	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Member.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Member obj = (Member) target;
		
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
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memId", "member.memId.required");
		
		if (obj.getMemId()!=null) {
			if (!this.lengthRange(obj.getMemId(), 1, 20)) {
				errors.rejectValue("memId", "member.memId.size");
			}
			
			Member o = this.memberRepo.findByMemIdIgnoreCase(obj.getMemId());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("memId", "member.memId.unique");
			}
			
			if (!this.isAlphaNumeric(obj.getMemId())) {
				errors.rejectValue("memId", "member.memId.format");
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
			
			Member o = this.memberRepo.findByMobile(obj.getMobile());
			if (o != null && !o.getId().equals(obj.getId())) {
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
			
			Member o = this.memberRepo.findByEmailIgnoreCase(obj.getEmail());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("email", "member.email.unique");
			}
			
			if (!this.isEmail(obj.getEmail())) {
				errors.rejectValue("email", "member.email.format");
			}
		}
		
		if (obj.getSubEndDate()==null) {
			errors.rejectValue("subEndDate", "member.subEndDate.required");
		}
		
		if (obj.getSubStartDate()==null) {
			errors.rejectValue("subStartDate", "member.subStartDate.required");
		}
		
		if (obj.getAadhaar()!=null) {
			if (!this.lengthRange(obj.getAadhaar(), 0, 20)) {
				errors.rejectValue("aadhaar", "member.aadhaar.size");
			}
		}
	}
}
