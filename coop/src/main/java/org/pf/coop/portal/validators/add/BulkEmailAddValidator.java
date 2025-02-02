package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.BulkEmail;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BulkEmailAddValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return BulkEmail.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		BulkEmail obj = (BulkEmail) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "bulkEmail.subject.required");

		if (obj.getSubject()!=null){
			if (!this.lengthRange(obj.getSubject(), 1, 200)){
				errors.rejectValue("subject", "bulkEmail.subject.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "bulkEmail.message.required");
		
	}
}
