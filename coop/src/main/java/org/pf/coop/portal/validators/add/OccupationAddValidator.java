package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Occupation;
import org.pf.coop.portal.repository.OccupationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OccupationAddValidator extends BaseValidator implements Validator {

	@Autowired
	private OccupationRepo occupationRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Occupation.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Occupation obj = (Occupation) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "occupation.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "occupation.name.size");
			}
			
			Occupation o = this.occupationRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "occupation.name.unique");
			}
		}
	}
}

