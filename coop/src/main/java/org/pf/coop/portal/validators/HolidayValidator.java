package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Holiday;
import org.pf.coop.portal.repository.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HolidayValidator extends BaseValidator implements Validator {
	
	@Autowired
	private HolidayRepo holidayRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Holiday.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Holiday obj = (Holiday) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "holiday.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "holiday.name.size");
			}
		}

		if (obj.getDate()==null){
			errors.rejectValue("date", "holiday.date.required");
		} else {
			Holiday o = this.holidayRepo.findByDate(obj.getDate());
			
			if (o != null) {
				errors.rejectValue("date", "holiday.date.unique");
			}
		}
	}
}
