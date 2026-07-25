package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Module;
import org.pf.coop.portal.repository.ModuleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ModuleEditValidator extends BaseValidator implements Validator {

	@Autowired
	private ModuleRepo moduleRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Module.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Module obj = (Module) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "module.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "module.name.size");
			}
			
			Module o = this.moduleRepo.findByName(obj.getName());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "module.name.unique");
			}
		}
		
		if (obj.getEnabled()==null) {
			errors.rejectValue("enabled", "module.enabled.required");
		}
	}
}
