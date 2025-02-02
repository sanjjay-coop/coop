package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.repository.EventTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventTypeAddValidator extends BaseValidator implements Validator {

	@Autowired
	private EventTypeRepo eventTypeRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return EventType.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		EventType obj = (EventType) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "eventType.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "eventType.name.size");
			}
			
			EventType o = this.eventTypeRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "eventType.name.unique");
			}
		}
	}
}
