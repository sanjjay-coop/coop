package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoleEditValidator extends BaseValidator implements Validator {

	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Role.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Role obj = (Role) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "role.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 50)){
				errors.rejectValue("code", "role.code.size");
			}
			
			Role o = this.roleRepo.findByCode(obj.getCode());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("code", "role.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "role.description.required");
		
		if (obj.getDescription()!=null){
			if (!this.lengthRange(obj.getDescription(), 1, 50)){
				errors.rejectValue("description", "role.description.size");
			}
			
			Role o = this.roleRepo.findByDescription(obj.getDescription());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("description", "role.description.unique");
			}
		}
		
	}
}
