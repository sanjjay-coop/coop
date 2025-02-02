package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Reminder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReminderValidator extends BaseValidator implements Validator {

	//@Autowired
	//private ReminderRepo reminderRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Reminder.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//Reminder obj = (Reminder) target;
		
	}
}
