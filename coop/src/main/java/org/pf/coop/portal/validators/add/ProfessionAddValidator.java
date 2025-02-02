package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Profession;
import org.pf.coop.portal.repository.ProfessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfessionAddValidator extends BaseValidator implements Validator {

	@Autowired
	private ProfessionRepo professionRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Profession.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Profession obj = (Profession) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "profession.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "profession.name.size");
			}
			
			Profession o = this.professionRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "profession.name.unique");
			}
		}
	}
}