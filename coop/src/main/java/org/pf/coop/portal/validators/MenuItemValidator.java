package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.MenuItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MenuItemValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return MenuItem.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MenuItem obj = (MenuItem) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "menuItem.title.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 50)){
				errors.rejectValue("title", "menuItem.title.size");
			}
		}

		if (obj.getItemType()!=null){
			switch(obj.getItemType()) {
			case "url":
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "menuItem.url.required");
				if (obj.getUrl()!=null) {
					if (!this.lengthRange(obj.getUrl(), 1, 255)) {
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "menuItem.url.size");
					}
				}
				break;
			case "category":
				if (obj.getCategory()==null) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "menuItem.category.required");
				}
				break;
			default:
				errors.rejectValue("itemType", "menuItem.itemType.required");
				break;
			}
		} else {
			errors.rejectValue("itemType", "menuItem.itemType.required");
		}
		
		if (obj.getNewPage()==null) {
			obj.setNewPage(false);
		}
	}
}
