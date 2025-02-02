package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.MaritalStatus;
import org.pf.coop.portal.repository.MaritalStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MaritalStatusAddValidator extends BaseValidator implements Validator {

	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return MaritalStatus.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MaritalStatus obj = (MaritalStatus) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "maritalStatus.status.required");
		
		if (obj.getStatus()!=null){
			if (!this.lengthRange(obj.getStatus(), 1, 50)){
				errors.rejectValue("status", "maritalStatus.status.size");
			}
			
			MaritalStatus o = this.maritalStatusRepo.findByStatus(obj.getStatus());
			if (o!=null) {
				errors.rejectValue("status", "maritalStatus.status.unique");
			}
		}
	}
}
