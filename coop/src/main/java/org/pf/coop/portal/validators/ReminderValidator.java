package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Reminder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ReminderValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Reminder.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Reminder obj = (Reminder) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "reminder.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "reminder.title.required");
		
		if (obj.getTitle()!=null) {
			if (!this.lengthRange(obj.getTitle(), 1, 255)) {
				errors.rejectValue("title", "reminder.title.size");
			}
		}

		if (obj.getDescription()!=null) {
			if (!this.lengthRange(obj.getDescription(), 1, 2000)) {
				errors.rejectValue("description", "reminder.description.size");
			}
		}
		
		if (obj.getRemDate()==null) {
			errors.rejectValue("remDate", "reminder.remDate.required");
		}
		
		if (obj.getRemDays()==null) {
			errors.rejectValue("remDays", "reminder.remDays.required");
		}
		
		if (obj.getRemByEmail()==null) {
			errors.rejectValue("remByEmail", "reminder.remByEmail.required");
		}
		
	}
}
