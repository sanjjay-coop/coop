package org.pf.coop.portal.validators.library.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.library.TitleType;
import org.pf.coop.portal.repository.library.TitleTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TitleTypeAddValidator extends BaseValidator implements Validator {

	@Autowired
	private TitleTypeRepo titleTypeRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return TitleType.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		TitleType obj = (TitleType) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "titleType.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "titleType.name.size");
			}
			
			TitleType o = this.titleTypeRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "titleType.name.unique");
			}
		}
	}
}
