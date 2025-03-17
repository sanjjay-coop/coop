package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Quotation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class QuotationValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Quotation.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Quotation obj = (Quotation) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quote", "quotation.quote.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "quotation.author.required");
		
		if (obj.getQuote()!=null){
			if (!this.lengthRange(obj.getQuote(), 1, 500)){
				errors.rejectValue("quote", "quotation.quote.size");
			}
		}
		
		if (obj.getAuthor()!=null){
			if (!this.lengthRange(obj.getAuthor(), 1, 100)){
				errors.rejectValue("author", "quotation.author.size");
			}
		}
		
		if (obj.getSource()!=null){
			if (!this.lengthRange(obj.getSource(), 0, 500)){
				errors.rejectValue("source", "quotation.source.size");
			}
		}
	}
}
