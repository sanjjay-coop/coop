package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Funding;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FundingValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Funding.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Funding obj = (Funding) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purpose", "funding.purpose.required");
		
		if (obj.getAmountRequested()==null) {
			errors.rejectValue("amountRequested", "funding.amountRequested.required");
		}
		

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "funding.name.required");
		
		if (obj.getName()!=null) {
			if (!this.lengthRange(obj.getName(), 1, 100)) {
				errors.rejectValue("name", "funding.name.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "funding.address.required");
		
		if (obj.getAddress()!=null) {
			if (!this.lengthRange(obj.getAddress(), 1, 100)) {
				errors.rejectValue("address", "funding.address.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "funding.city.required");
		
		if (obj.getCity()!=null) {
			if (!this.lengthRange(obj.getCity(), 1, 50)) {
				errors.rejectValue("city", "funding.city.size");
			}
		}
		
		if (obj.getPin()!=null) {
			if (!this.lengthRange(obj.getPin(), 0, 10)) {
				errors.rejectValue("pin", "funding.pin.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "funding.state.required");
		
		if (obj.getState()!=null) {
			if (!this.lengthRange(obj.getState(), 1, 50)) {
				errors.rejectValue("state", "funding.state.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "funding.country.required");
		
		if (obj.getCountry()!=null) {
			if (!this.lengthRange(obj.getCountry(), 1, 50)) {
				errors.rejectValue("country", "funding.country.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "funding.email.required");
		
		if (obj.getEmail()!=null) {
			if (!this.lengthRange(obj.getEmail(), 1, 50)) {
				errors.rejectValue("email", "funding.email.size");
			} else {
				if (!this.isEmail(obj.getEmail())) {
					errors.rejectValue("email", "funding.email.valid");
				}
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "funding.mobile.required");
		
		if (obj.getMobile()!=null) {
			if (!this.lengthRange(obj.getMobile(), 1, 10)) {
				errors.rejectValue("mobile", "funding.mobile.size");
			} else {
				if (!this.isNumeric(obj.getMobile())) {
					errors.rejectValue("mobile", "funding.mobile.numeric");
				}
			}
		}
	}
}
