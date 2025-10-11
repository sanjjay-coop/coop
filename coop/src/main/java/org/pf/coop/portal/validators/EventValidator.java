package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Event.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Event obj = (Event) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "event.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "event.description.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "event.title.size");
			}
		}

		if (obj.getStartDate()==null) {
			errors.rejectValue("startDate", "event.startDate.required");
		}

		if (obj.getEndDate()==null) {
			errors.rejectValue("endDate", "event.endDate.required");
		}
		
		if (obj.getEventType()==null) {
			errors.rejectValue("eventType", "event.eventType.required");
		}
		
		if (obj.getPublish()==null) {
			errors.rejectValue("publish", "event.publish.required");
		}	
		
		if (obj.getVenue()!=null){
			if (!this.lengthRange(obj.getVenue(), 0, 100)){
				errors.rejectValue("venue", "event.venue.size");
			}
		}
		
		if (obj.getCity()!=null){
			if (!this.lengthRange(obj.getCity(), 0, 100)){
				errors.rejectValue("city", "event.city.size");
			}
		}
		
		if (obj.getState()!=null){
			if (!this.lengthRange(obj.getState(), 0, 100)){
				errors.rejectValue("state", "event.state.size");
			}
		}
	}
}
