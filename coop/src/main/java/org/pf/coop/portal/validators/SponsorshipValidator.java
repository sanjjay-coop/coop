package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Sponsorship;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SponsorshipValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Sponsorship.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Sponsorship obj = (Sponsorship) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "sponsorship.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "sponsorship.description.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "sponsorship.title.size");
			}
		}

		if (obj.getDescription()!=null){
			if (obj.getDescription().length()<1){
				errors.rejectValue("description", "sponsorship.description.required");
			}
		}

		if (obj.getPubDate()==null){
			errors.rejectValue("pubDate", "sponsorship.pubDate.required");
		}

		if (obj.getExpDate()==null){
			errors.rejectValue("expDate", "sponsorship.expDate.required");
		}

		if (obj.getExpDate()==null){
			errors.rejectValue("lastDate", "sponsorship.lastDate.required");
		}
	}
}
