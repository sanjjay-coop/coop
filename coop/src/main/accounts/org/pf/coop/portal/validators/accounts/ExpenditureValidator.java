package org.pf.coop.portal.validators.accounts;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.accounts.Expenditure;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ExpenditureValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Expenditure.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Expenditure obj = (Expenditure) target;
				
		if (obj.getAmount()==null) {
			errors.rejectValue("amount", "expenditure.amount.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "narration", "expenditure.narration.required");
		
		if (obj.getNarration()!=null){
			if (!this.lengthRange(obj.getNarration(), 1, 500)){
				errors.rejectValue("narration", "expenditure.narration.size");
			}
		}
		
		if (obj.getTransactionDate()==null) {
			errors.rejectValue("transactionDate", "expenditure.transactionDate.required");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voucherInvoiceNumber", "expenditure.voucherInvoiceNumber.required");
		
		if (obj.getVoucherInvoiceNumber()!=null){
			if (!this.lengthRange(obj.getVoucherInvoiceNumber(), 1, 20)){
				errors.rejectValue("voucherInvoiceNumber", "expenditure.voucherInvoiceNumber.size");
			}
		}
		
		if (obj.getVoucherDate()==null) {
			errors.rejectValue("voucherDate", "expenditure.voucherDate.required");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paidTo", "expenditure.paidTo.required");
		
		if (obj.getPaidTo()!=null){
			if (!this.lengthRange(obj.getPaidTo(), 1, 100)){
				errors.rejectValue("paidTo", "expenditure.paidTo.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "modeOfPayment", "expenditure.modeOfPayment.required");
		
		if (obj.getModeOfPayment()!=null){
			if (!this.lengthRange(obj.getModeOfPayment(), 1, 20)){
				errors.rejectValue("modeOfPayment", "expenditure.modeOfPayment.size");
			}
		}
		
		if (obj.getHeadOfAccount()==null) {
			errors.rejectValue("headOfAccount", "expenditure.headOfAccount.required");
		}

	}
}

