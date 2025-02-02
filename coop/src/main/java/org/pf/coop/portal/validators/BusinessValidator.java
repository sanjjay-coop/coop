package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Business;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BusinessValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Business.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Business obj = (Business) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessName", "business.businessName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "business.description.required");
		
		if (obj.getBusinessName()!=null){
			if (!this.lengthRange(obj.getBusinessName(), 1, 200)){
				errors.rejectValue("businessName", "business.businessName.size");
			}
		}
		
		if (obj.getDescription()!=null){
			if (!this.lengthRange(obj.getDescription(), 1, 2000)){
				errors.rejectValue("description", "business.description.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "business.address.required");
		
		if (obj.getAddress()!=null){
			if (!this.lengthRange(obj.getAddress(), 1, 100)){
				errors.rejectValue("address", "business.address.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "business.city.required");
		
		if (obj.getCity()!=null){
			if (!this.lengthRange(obj.getCity(), 1, 50)){
				errors.rejectValue("city", "business.city.size");
			}
		}
		
		if (obj.getPin()!=null) {
			if (!this.lengthRange(obj.getPin(), 0, 10)) {
				errors.rejectValue("pin", "business.pin.size");
			}
		}
		
		if (obj.getState()!=null) {
			if (!this.lengthRange(obj.getState(), 0, 50)) {
				errors.rejectValue("state", "business.state.size");
			}
		}
		
		if (obj.getCountry()!=null) {
			if (!this.lengthRange(obj.getCountry(), 0, 50)) {
				errors.rejectValue("country", "business.country.size");
			}
		}
	
		if (obj.getContactName()!=null){
			if (!this.lengthRange(obj.getContactName(), 0, 50)){
				errors.rejectValue("contactName", "business.contactName.size");
			}
		}
		
		if (obj.getContactPhone()!=null){
			if (!this.lengthRange(obj.getContactPhone(), 0, 20)){
				errors.rejectValue("contactPhone", "business.contactPhone.size");
			} else if (obj.getContactPhone().length()>0 && !this.isNumeric(obj.getContactPhone())) {
				errors.rejectValue("contactPhone", "business.contactPhone.format");
			}
		}
		
		if (obj.getContactEmail()!=null){
			if (!this.lengthRange(obj.getContactEmail(), 0, 255)){
				errors.rejectValue("contactEmail", "business.contactEmail.size");
			} else if (obj.getContactEmail().length()>0 && !this.isEmail(obj.getContactEmail())) {
				errors.rejectValue("contactEmail", "business.contactEmail.format");
			}
		}
		
		if (obj.getUrl()!=null){
			if (!this.lengthRange(obj.getUrl(), 0, 100)){
				errors.rejectValue("url", "business.url.size");
			}
		}
		
		if (obj.getKeywords()!=null){
			if (!this.lengthRange(obj.getKeywords(), 0, 500)){
				errors.rejectValue("keywords", "business.keywords.size");
			}
		}
	}
}
