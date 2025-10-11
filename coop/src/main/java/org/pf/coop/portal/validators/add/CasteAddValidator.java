package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Caste;
import org.pf.coop.portal.repository.CasteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CasteAddValidator extends BaseValidator implements Validator {

	@Autowired
	private CasteRepo casteRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Caste.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Caste obj = (Caste) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "caste.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "caste.name.size");
			}
			
			Caste o = this.casteRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "caste.name.unique");
			}
		}
	}
}

