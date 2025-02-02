package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.MemberGroup;
import org.pf.coop.portal.repository.MemberGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberGroupAddValidator extends BaseValidator implements Validator {

	@Autowired
	private MemberGroupRepo memberGroupRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return MemberGroup.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MemberGroup obj = (MemberGroup) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "memberGroup.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "memberGroup.name.size");
			}
			
			MemberGroup o = this.memberGroupRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "memberGroup.name.unique");
			}
		}
	}
}

