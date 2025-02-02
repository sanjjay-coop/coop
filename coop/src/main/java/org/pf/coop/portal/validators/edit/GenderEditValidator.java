package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Gender;
import org.pf.coop.portal.repository.GenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GenderEditValidator extends BaseValidator implements Validator {

	@Autowired
	private GenderRepo genderRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Gender.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Gender obj = (Gender) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "gender.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 20)){
				errors.rejectValue("name", "gender.name.size");
			}
			
			Gender o = this.genderRepo.findByName(obj.getName());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "gender.name.unique");
			}
		}
		
	}
}
