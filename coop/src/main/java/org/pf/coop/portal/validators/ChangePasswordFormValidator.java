package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.forms.ChangePasswordForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordFormValidator extends BaseValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return ChangePasswordForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ChangePasswordForm obj = (ChangePasswordForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "cpf.currentPassword.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "cpf.newPassword.required");
		
		if (obj.getNewPassword()!=null){
			if (!this.lengthRange(obj.getNewPassword(), 6, 100)){
				errors.rejectValue("newPassword", "cpf.newPassword.size");
			} else {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retypeNewPassword", "cpf.retypeNewPassword.required");
				if (obj.getRetypeNewPassword()!=null) {
					if (!obj.getNewPassword().equals(obj.getRetypeNewPassword())){
						errors.rejectValue("retypeNewPassword", "cpf.retypeNewPassword.same");
					}
				}
			}
		}
	}
}
