package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Matrimonial;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MatrimonialValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Matrimonial.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Matrimonial obj = (Matrimonial) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lookingFor", "matrimonial.lookingFor.required");
		
		if (obj.getLookingFor()!=null) {
			if (!this.lengthRange(obj.getLookingFor(), 1, 20)) {
				errors.rejectValue("lookingFor", "matrimonial.lookingFor.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "matrimonial.description.required");
		
		if (obj.getDescription()!=null) {
			if (!this.lengthRange(obj.getDescription(), 1, 2000)) {
				errors.rejectValue("description", "matrimonial.description.size");
			}
		}
		
		if (obj.getAge()==null){
			errors.rejectValue("age", "matrimonial.age.required");
		}
		
		if (obj.getEducationLevel()==null){
			errors.rejectValue("educationLevel", "matrimonial.educationLevel.required");
		}
		
		if (obj.getOccupation()==null){
			errors.rejectValue("occupation", "matrimonial.occupation.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expDescription", "matrimonial.expDescription.required");
		
		if (obj.getExpDescription()!=null) {
			if (!this.lengthRange(obj.getExpDescription(), 1, 2000)) {
				errors.rejectValue("expDescription", "matrimonial.expDescription.size");
			}
		}
		
		if (obj.getExpMinAge()==null){
			errors.rejectValue("expMinAge", "matrimonial.expMinAge.required");
		}
		
		if (obj.getExpMaxAge()==null){
			errors.rejectValue("expMaxAge", "matrimonial.expMaxAge.required");
		}
		
		if (obj.getExpMinAge()!=null && obj.getExpMaxAge()!=null){
			if (obj.getExpMaxAge()<obj.getExpMinAge()) {
				errors.rejectValue("expMinAge", "matrimonial.expMinAge.value");
			}
		}
		
		if (obj.getExpEducationLevel()==null){
			errors.rejectValue("expEducationLevel", "matrimonial.expEducationLevel.required");
		}
		
		if (obj.getExpOccupation()==null){
			errors.rejectValue("expOccupation", "matrimonial.expOccupation.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "matrimonial.mobile.required");
		
		if (obj.getMobile()!=null){
			if (!this.lengthRange(obj.getMobile(), 1, 10)){
				errors.rejectValue("mobile", "matrimonial.mobile.size");
			}
			
			if (obj.getMobile().length()>0 && !this.isNumeric(obj.getMobile())) {
				errors.rejectValue("mobile", "matrimonial.mobile.format");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "matrimonial.email.required");
		
		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 1, 255)){
				errors.rejectValue("email", "matrimonial.email.size");
			}
			
			if (obj.getEmail().length()>0 && !this.isEmail(obj.getEmail())) {
				errors.rejectValue("email", "matrimonial.email.format");
			}
		}
	}
}


