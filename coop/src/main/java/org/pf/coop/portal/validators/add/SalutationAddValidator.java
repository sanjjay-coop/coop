package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Salutation;
import org.pf.coop.portal.repository.SalutationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SalutationAddValidator extends BaseValidator implements Validator {

	@Autowired
	private SalutationRepo salutationRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Salutation.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Salutation obj = (Salutation) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "salutation.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "salutation.name.size");
			}
			
			Salutation o = this.salutationRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "salutation.name.unique");
			}
		}
	}
}
