package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.MemberType;
import org.pf.coop.portal.repository.MemberTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberTypeEditValidator extends BaseValidator implements Validator {

	@Autowired
	private MemberTypeRepo memberTypeRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return MemberType.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MemberType obj = (MemberType) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "memberType.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "memberType.name.size");
			}
			
			MemberType o = this.memberTypeRepo.findByName(obj.getName());
			if (o != null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "memberType.name.unique");
			}
		}
		
	}
}
