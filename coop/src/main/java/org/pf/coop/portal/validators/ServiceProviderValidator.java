package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.ServiceProvider;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ServiceProviderValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return ServiceProvider.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ServiceProvider obj = (ServiceProvider) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceName", "serviceProvider.serviceName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "serviceProvider.description.required");
		
		if (obj.getServiceName()!=null){
			if (!this.lengthRange(obj.getServiceName(), 1, 200)){
				errors.rejectValue("serviceProviderName", "serviceProvider.serviceProviderName.size");
			}
		}
		
		if (obj.getDescription()!=null){
			if (!this.lengthRange(obj.getDescription(), 1, 3000)){
				errors.rejectValue("description", "serviceProvider.description.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "serviceProvider.address.required");
		
		if (obj.getAddress()!=null){
			if (!this.lengthRange(obj.getAddress(), 1, 100)){
				errors.rejectValue("address", "serviceProvider.address.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "serviceProvider.city.required");
		
		if (obj.getCity()!=null){
			if (!this.lengthRange(obj.getCity(), 1, 50)){
				errors.rejectValue("city", "serviceProvider.city.size");
			}
		}
		
		if (obj.getPin()!=null) {
			if (!this.lengthRange(obj.getPin(), 0, 10)) {
				errors.rejectValue("pin", "serviceProvider.pin.size");
			}
		}
		
		if (obj.getState()!=null) {
			if (!this.lengthRange(obj.getState(), 0, 50)) {
				errors.rejectValue("state", "serviceProvider.state.size");
			}
		}
		
		if (obj.getCountry()!=null) {
			if (!this.lengthRange(obj.getCountry(), 0, 50)) {
				errors.rejectValue("country", "serviceProvider.country.size");
			}
		}
	
		if (obj.getContactName()!=null){
			if (!this.lengthRange(obj.getContactName(), 0, 50)){
				errors.rejectValue("contactName", "serviceProvider.contactName.size");
			}
		}
		
		if (obj.getContactPhone()!=null){
			if (!this.lengthRange(obj.getContactPhone(), 0, 20)){
				errors.rejectValue("contactPhone", "serviceProvider.contactPhone.size");
			} else if (obj.getContactPhone().length()>0 && !this.isNumeric(obj.getContactPhone())) {
				errors.rejectValue("contactPhone", "serviceProvider.contactPhone.format");
			}
		}
		
		if (obj.getContactEmail()!=null){
			if (!this.lengthRange(obj.getContactEmail(), 0, 255)){
				errors.rejectValue("contactEmail", "serviceProvider.contactEmail.size");
			} else if (obj.getContactEmail().length()>0 && !this.isEmail(obj.getContactEmail())) {
				errors.rejectValue("contactEmail", "serviceProvider.contactEmail.format");
			}
		}
		
		if (obj.getKeywords()!=null){
			if (!this.lengthRange(obj.getKeywords(), 0, 500)){
				errors.rejectValue("keywords", "serviceProvider.keywords.size");
			}
		}
	}
}
