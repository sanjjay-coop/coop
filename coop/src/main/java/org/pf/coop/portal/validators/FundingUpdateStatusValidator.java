package org.pf.coop.portal.validators;

import java.math.BigDecimal;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Funding;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FundingUpdateStatusValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Funding.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Funding obj = (Funding) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "funding.status.required");
		
		if (obj.getStatus()==null) {
			errors.rejectValue("status", "funding.status.required");
		} else {
			if (obj.getStatus().equals("RECEIVED")) {
				errors.rejectValue("status", "funding.status.notChanged");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "remarks", "funding.remarks.required");
		
		if (obj.getRemarks()!=null) {
			if (!this.lengthRange(obj.getRemarks(), 1, 500)) {
				errors.rejectValue("remarks", "funding.remarks.size");
			}
		}
		
		if (obj.getAmountSanctioned()==null) {
			errors.rejectValue("amountSanctioned", "funding.amountSanctioned.required");
		} else {
			if (obj.getAmountSanctioned().compareTo(new BigDecimal(0))<1) {
				errors.rejectValue("amountSanctioned", "funding.amountSanctioned.value");
			}
		}
		
		if (obj.getSanctionDate()==null) {
			errors.rejectValue("sanctionDate", "funding.sanctionDate.required");
		}
	}
}
