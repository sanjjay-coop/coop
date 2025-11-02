package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.repository.OrderCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OrderCategoryEditValidator extends BaseValidator implements Validator {

	@Autowired
	private OrderCategoryRepo orderCategoryRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return OrderCategory.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		OrderCategory obj = (OrderCategory) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "orderCategory.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "orderCategory.name.size");
			}
			
			OrderCategory o = this.orderCategoryRepo.findByName(obj.getName());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "orderCategory.name.unique");
			}
		}
	}
}

