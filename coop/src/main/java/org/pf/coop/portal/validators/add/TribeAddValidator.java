package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Tribe;
import org.pf.coop.portal.repository.TribeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TribeAddValidator extends BaseValidator implements Validator {

	@Autowired
	private TribeRepo tribeRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Tribe.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Tribe obj = (Tribe) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "tribe.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "tribe.name.size");
			}
			
			Tribe o = this.tribeRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "tribe.name.unique");
			}
		}
	}
}
