package org.pf.coop.portal.validators;

import java.util.Calendar;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Job;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class JobValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Job.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Job obj = (Job) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", "job.position.required");
		
		if (obj.getPosition()!=null){
			if (!this.lengthRange(obj.getPosition(), 1, 50)){
				errors.rejectValue("position", "job.position.size");
			}
		}
		
		if (obj.getSalary()==null) {
			errors.rejectValue("salary", "job.salary.required");
		}
		
		if (obj.getMinimumQualification()==null) {
			errors.rejectValue("minimumQualification", "job.minimumQualification.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firmName", "job.firmName.required");
		
		if (obj.getFirmName()!=null){
			if (!this.lengthRange(obj.getFirmName(), 1, 200)){
				errors.rejectValue("firmName", "job.firmName.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "job.address.required");
		
		if (obj.getAddress()!=null){
			if (!this.lengthRange(obj.getAddress(), 1, 100)){
				errors.rejectValue("address", "job.address.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "job.city.required");
		
		if (obj.getCity()!=null) {
			if (!this.lengthRange(obj.getCity(), 1, 50)) {
				errors.rejectValue("city", "job.city.size");
			}
		}

		if (obj.getPin()!=null) {
			if (!this.lengthRange(obj.getPin(), 0, 10)) {
				errors.rejectValue("pin", "job.pin.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "job.state.required");
		
		if (obj.getState()!=null) {
			if (!this.lengthRange(obj.getState(), 1, 50)) {
				errors.rejectValue("state", "job.state.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "job.country.required");
		
		if (obj.getCountry()!=null) {
			if (!this.lengthRange(obj.getCountry(), 1, 50)) {
				errors.rejectValue("country", "job.country.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactName", "job.contactName.required");
		
		if (obj.getContactName()!=null){
			if (!this.lengthRange(obj.getContactName(), 1, 50)){
				errors.rejectValue("contactName", "job.contactName.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactDesignation", "job.contactDesignation.required");
		
		if (obj.getContactDesignation()!=null){
			if (!this.lengthRange(obj.getContactDesignation(), 1, 50)){
				errors.rejectValue("contactDesignation", "job.contactDesignation.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "job.contactPhone.required");
		
		if (obj.getContactPhone()!=null){
			if (!this.lengthRange(obj.getContactPhone(), 1, 10)){
				errors.rejectValue("contactPhone", "job.contactPhone.size");
			}
			
			if (obj.getContactPhone().length()>0 && !this.isNumeric(obj.getContactPhone())) {
				errors.rejectValue("contactPhone", "job.contactPhone.format");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactEmail", "job.contactEmail.required");
		
		if (obj.getContactEmail()!=null){
			if (!this.lengthRange(obj.getContactEmail(), 1, 255)){
				errors.rejectValue("contactEmail", "job.contactEmail.size");
			}
			
			if (obj.getContactEmail().length()>0 && !this.isEmail(obj.getContactEmail())) {
				errors.rejectValue("contactEmail", "job.contactEmail.format");
			}
		}
		
		if (obj.getUrl()!=null){
			if (!this.lengthRange(obj.getUrl(), 0, 100)){
				errors.rejectValue("url", "job.url.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "job.description.required");
		
		if (obj.getDescription()!=null){
			if (!this.lengthRange(obj.getDescription(), 1, 3000)){
				errors.rejectValue("description", "job.description.size");
			}
		}
		
		if (obj.getLastDate()!=null){
			if (obj.getLastDate().before(Calendar.getInstance().getTime())){
				errors.rejectValue("lastDate", "job.lastDate.valid");
			}
		} else {
			errors.rejectValue("lastDate", "job.lastDate.required");
		}
	}
}
