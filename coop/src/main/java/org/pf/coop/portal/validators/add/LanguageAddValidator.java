package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Language;
import org.pf.coop.portal.repository.LanguageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LanguageAddValidator extends BaseValidator implements Validator {

	@Autowired
	private LanguageRepo languageRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Language.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Language obj = (Language) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "language.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "language.name.size");
			}
			
			Language o = this.languageRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "language.name.unique");
			}
		}
	}
}

