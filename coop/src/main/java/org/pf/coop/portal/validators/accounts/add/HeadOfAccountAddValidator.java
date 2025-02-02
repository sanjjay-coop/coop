package org.pf.coop.portal.validators.accounts.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.accounts.HeadOfAccount;
import org.pf.coop.portal.repository.accounts.HeadOfAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HeadOfAccountAddValidator extends BaseValidator implements Validator {

	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return HeadOfAccount.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		HeadOfAccount obj = (HeadOfAccount) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "headOfAccount.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 20)){
				errors.rejectValue("code", "headOfAccount.code.size");
			}
			
			HeadOfAccount o = this.headOfAccountRepo.findByCode(obj.getCode());
			if (o!=null) {
				errors.rejectValue("code", "headOfAccount.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "headOfAccount.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "headOfAccount.name.size");
			}
			
			HeadOfAccount o = this.headOfAccountRepo.findByName(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "headOfAccount.name.unique");
			}
		}
	}
}
