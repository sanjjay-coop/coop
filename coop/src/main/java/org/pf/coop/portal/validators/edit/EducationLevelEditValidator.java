package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.EducationLevel;
import org.pf.coop.portal.repository.EducationLevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EducationLevelEditValidator extends BaseValidator implements Validator {

	@Autowired
	private EducationLevelRepo educationLevelRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return EducationLevel.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		EducationLevel obj = (EducationLevel) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "educationLevel.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "educationLevel.name.size");
			}
			
			EducationLevel o = this.educationLevelRepo.findByName(obj.getName());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "educationLevel.name.unique");
			}
		}
		
	}
}

