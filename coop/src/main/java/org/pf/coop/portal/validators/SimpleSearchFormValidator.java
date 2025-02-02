package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.forms.SimpleSearchForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SimpleSearchFormValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return SimpleSearchForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SimpleSearchForm obj = (SimpleSearchForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchString", "ssf.searchString.required");
		
		if (obj.getSearchString()!=null) {
			if (!this.lengthRange(obj.getSearchString(), 3, 50)) {
				errors.rejectValue("searchString", "ssf.searchString.size");
			}
		}

	}
}
