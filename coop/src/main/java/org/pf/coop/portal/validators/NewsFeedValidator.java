package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.NewsFeed;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NewsFeedValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return NewsFeed.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		NewsFeed obj = (NewsFeed) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "newsFeed.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "newsFeed.description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "newsFeed.url.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 100)){
				errors.rejectValue("title", "newsFeed.title.size");
			}
		}
		
		if (obj.getDescription()!=null){
			if (!this.lengthRange(obj.getDescription(), 1, 500)){
				errors.rejectValue("description", "newsFeed.description.size");
			}
		}
		
		if (obj.getUrl()!=null){
			if (!this.lengthRange(obj.getUrl(), 1, 255)){
				errors.rejectValue("url", "newsFeed.url.size");
			}
		}
	}
}
