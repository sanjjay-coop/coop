package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Member;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberSelfValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Member.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Member obj = (Member) target;
		
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
		
		if (obj.getEducation()!=null) {
			if (!this.lengthRange(obj.getEducation(), 0, 500)) {
				errors.rejectValue("education", "member.education.size");
			}
		}
		
		if (obj.getAadhaar()!=null) {
			if (!this.lengthRange(obj.getAadhaar(), 0, 20)) {
				errors.rejectValue("aadhaar", "member.aadhaar.size");
			}
		}
		
		if (obj.getAadhaar()!=null) {
			if (!this.lengthRange(obj.getAadhaar(), 0, 20)) {
				errors.rejectValue("aadhaar", "member.aadhaar.size");
			}
		}
		
		if (obj.getProfilePublic()==null) {
			obj.setProfilePublic(false);
		}
	}
}
