package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.EventUpdate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventUpdateValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return EventUpdate.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		EventUpdate obj = (EventUpdate) target;
		
		if (obj.getEvent()==null) {
			errors.rejectValue("event", "eventUpdate.event.required");
		}
		
		ValidationUtils.rejectIfEmpty(errors, "description", "event.description.required");
		
		if (obj.getDescription()!=null) {
			if (!this.lengthRange(obj.getDescription(), 1,2000)) {
				errors.rejectValue("description", "eventUpdate.description.size");
			}
		}		
	}
}
