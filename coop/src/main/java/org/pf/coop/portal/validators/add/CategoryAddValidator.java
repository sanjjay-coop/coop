package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Category;
import org.pf.coop.portal.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryAddValidator extends BaseValidator implements Validator {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Category.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Category obj = (Category) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "category.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "category.name.size");
			}
			
			Category o = this.categoryRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "category.name.unique");
			}
		}
	}
}
