package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Advert;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AdvertValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Advert.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Advert obj = (Advert) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "advert.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "advert.location.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "advert.content.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "advert.name.size");
			}
		}

		if (obj.getLocation()!=null){
			if (!this.lengthRange(obj.getLocation(), 1, 20)){
				errors.rejectValue("location", "advert.location.size");
			}
		}

		if (obj.getContent()!=null){
			if (!this.lengthRange(obj.getContent(), 1, 1000)){
				errors.rejectValue("content", "advert.content.size");
			}
		}

		if (obj.getPubDate()==null){
			errors.rejectValue("pubDate", "advert.pubDate.required");
		}

		if (obj.getExpDate()==null){
			errors.rejectValue("expDate", "advert.expDate.required");
		}
	}
}
