package org.pf.coop.portal.validators.library.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.library.Library;
import org.pf.coop.portal.repository.library.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LibraryAddValidator extends BaseValidator implements Validator {

	@Autowired
	private LibraryRepo libraryRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Library.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Library obj = (Library) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortName", "library.shortName.required");
		
		if (obj.getShortName()!=null) {
			if (!this.lengthRange(obj.getShortName(), 1, 10)) {
				errors.rejectValue("shortName", "library.shortName.size");
			}
			
			Library o = this.libraryRepo.findByShortName(obj.getShortName());
			
			if (o!=null) {
				errors.rejectValue("shortName", "library.shortName.unique");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "library.name.required");
		
		if (obj.getName()!=null) {
			if (!this.lengthRange(obj.getName(), 1, 100)) {
				errors.rejectValue("name", "library.name.size");
			}
			
			Library o = this.libraryRepo.findByName(obj.getName());
			
			if (o!=null) {
				errors.rejectValue("name", "library.name.unique");
			}
		}
		
		if (obj.getAddress()!=null) {
			if (!this.lengthRange(obj.getAddress(), 0, 50)) {
				errors.rejectValue("address", "library.address.size");
			}
		}
		
		if (obj.getCity()!=null) {
			if (!this.lengthRange(obj.getCity(), 0, 50)) {
				errors.rejectValue("city", "library.city.size");
			}
		}
		
		if (obj.getPin()!=null) {
			if (!this.lengthRange(obj.getPin(), 0, 10)) {
				errors.rejectValue("pin", "library.pin.size");
			}
		}
		
		if (obj.getState()!=null) {
			if (!this.lengthRange(obj.getState(), 0, 50)) {
				errors.rejectValue("state", "library.state.size");
			}
		}
		
		if (obj.getCountry()!=null) {
			if (!this.lengthRange(obj.getCountry(), 0, 50)) {
				errors.rejectValue("country", "library.country.size");
			}
		}
		
		if (obj.getMobile()!=null) {
			if (!this.lengthRange(obj.getMobile(), 0, 10)) {
				errors.rejectValue("mobile", "library.mobile.size");
			} else {
				if (obj.getMobile().length() > 0 && !this.isNumeric(obj.getMobile())) {
					errors.rejectValue("mobile", "library.mobile.format");
				}
			}
		}
		
		if (obj.getPhone()!=null) {
			if (!this.lengthRange(obj.getPhone(), 0, 10)) {
				errors.rejectValue("phone", "library.phone.size");
			} else {
				if (obj.getPhone().length() > 0 && !this.isNumeric(obj.getPhone())) {
					errors.rejectValue("phone", "library.phone.format");
				}
			}
		}
		
		if (obj.getEmail()!=null) {
			if (!this.lengthRange(obj.getEmail(), 0, 100)) {
				errors.rejectValue("email", "library.email.size");
			} else {
				if (obj.getEmail().length() > 0 && !this.isEmail(obj.getEmail())) {
					errors.rejectValue("email", "library.email.format");
				}
			}
		}
	}
}
