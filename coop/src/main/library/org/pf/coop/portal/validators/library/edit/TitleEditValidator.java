package org.pf.coop.portal.validators.library.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.library.Title;
import org.pf.coop.portal.repository.library.TitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TitleEditValidator extends BaseValidator implements Validator {

	@Autowired
	private TitleRepo titleRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Title.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Title obj = (Title) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accessionNumber", "title.accessionNumber.required");
		
		if (obj.getAccessionNumber()!=null) {
			if (!this.lengthRange(obj.getAccessionNumber(), 1, 10)) {
				errors.rejectValue("accessionNumber", "title.accessionNumber.size");
			} else {
				if (!this.isAlphaNumeric(obj.getAccessionNumber())) {
					errors.rejectValue("accessionNumber", "title.accessionNumber.unique");
				}
			}
			
			Title o = this.titleRepo.findByAccessionNumber(obj.getAccessionNumber());
			if (o!=null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("accessionNumber", "title.accessionNumber.unique");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uniformTitle", "title.uniformTitle.required");
		
		if (obj.getUniformTitle()!=null) {
			if (!this.lengthRange(obj.getUniformTitle(), 1, 1000)) {
				errors.rejectValue("uniformTitle", "title.uniformTitle.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authors", "title.authors.required");
		
		if (obj.getAuthors()!=null) {
			if (!this.lengthRange(obj.getAuthors(), 1, 1000)) {
				errors.rejectValue("authors", "title.authors.size");
			}
		}
		
		if (obj.getPublisher()!=null) {
			if (!this.lengthRange(obj.getPublisher(), 0, 1000)) {
				errors.rejectValue("publisher", "title.publisher.size");
			}
		}
		
		if (obj.getLibrary()==null) {
			errors.rejectValue("library", "title.library.required");
		}
		
		if (obj.getTitleType()==null) {
			errors.rejectValue("titleType", "title.titleType.required");
		}
		
		if (obj.getSummary()!=null) {
			if (!this.lengthRange(obj.getSummary(), 0, 2000)) {
				errors.rejectValue("summary", "title.summary.size");
			}
		}
	}
}
