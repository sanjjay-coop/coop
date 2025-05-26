package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Organization;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OrganizationValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Organization.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Organization obj = (Organization) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationName", "organization.organizationName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "organization.description.required");
		
		if (obj.getOrganizationName()!=null){
			if (!this.lengthRange(obj.getOrganizationName(), 1, 200)){
				errors.rejectValue("organizationName", "organization.organizationName.size");
			}
		}
		
		if (obj.getDescription()!=null){
			if (!this.lengthRange(obj.getDescription(), 1, 2000)){
				errors.rejectValue("description", "organization.description.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "organization.address.required");
		
		if (obj.getAddress()!=null){
			if (!this.lengthRange(obj.getAddress(), 1, 100)){
				errors.rejectValue("address", "organization.address.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "organization.city.required");
		
		if (obj.getCity()!=null){
			if (!this.lengthRange(obj.getCity(), 1, 50)){
				errors.rejectValue("city", "organization.city.size");
			}
		}
		
		if (obj.getPin()!=null) {
			if (!this.lengthRange(obj.getPin(), 0, 10)) {
				errors.rejectValue("pin", "organization.pin.size");
			}
		}
		
		if (obj.getState()!=null) {
			if (!this.lengthRange(obj.getState(), 0, 50)) {
				errors.rejectValue("state", "organization.state.size");
			}
		}
		
		if (obj.getCountry()!=null) {
			if (!this.lengthRange(obj.getCountry(), 0, 50)) {
				errors.rejectValue("country", "organization.country.size");
			}
		}
	
		if (obj.getContactName()!=null){
			if (!this.lengthRange(obj.getContactName(), 0, 50)){
				errors.rejectValue("contactName", "organization.contactName.size");
			}
		}
		
		if (obj.getContactPhone()!=null){
			if (!this.lengthRange(obj.getContactPhone(), 0, 20)){
				errors.rejectValue("contactPhone", "organization.contactPhone.size");
			} else if (obj.getContactPhone().length()>0 && !this.isNumeric(obj.getContactPhone())) {
				errors.rejectValue("contactPhone", "organization.contactPhone.format");
			}
		}
		
		if (obj.getContactEmail()!=null){
			if (!this.lengthRange(obj.getContactEmail(), 0, 255)){
				errors.rejectValue("contactEmail", "organization.contactEmail.size");
			} else if (obj.getContactEmail().length()>0 && !this.isEmail(obj.getContactEmail())) {
				errors.rejectValue("contactEmail", "organization.contactEmail.format");
			}
		}
		
		if (obj.getUrl()!=null){
			if (!this.lengthRange(obj.getUrl(), 0, 100)){
				errors.rejectValue("url", "organization.url.size");
			}
		}
	}
}
