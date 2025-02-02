package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.repository.InvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InvitationValidator extends BaseValidator implements Validator {
	
	@Autowired
	private InvitationRepo invitationRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Invitation.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Invitation obj = (Invitation) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invitation.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "invitation.name.required");
		
		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 1, 100)){
				errors.rejectValue("email", "invitation.email.size");
			}
			
			
			if (obj.getEmail().length()>0 && !this.isEmail(obj.getEmail())) {
				errors.rejectValue("email", "invitation.email.valid");
			}
			
			Invitation inv = this.invitationRepo.findByEmail(obj.getEmail());
			
			if (inv!=null) {
				errors.rejectValue("email", "invitation.email.unique");
			}
		}

		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "invitation.name.size");
			}
		}
	}
}

