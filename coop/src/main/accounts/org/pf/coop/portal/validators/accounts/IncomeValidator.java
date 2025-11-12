package org.pf.coop.portal.validators.accounts;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.accounts.Income;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class IncomeValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Income.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Income obj = (Income) target;
				
		if (obj.getAmount()==null) {
			errors.rejectValue("amount", "income.amount.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "towards", "income.towards.required");
		
		if (obj.getTowards()!=null){
			if (!this.lengthRange(obj.getTowards(), 1, 100)){
				errors.rejectValue("towards", "income.towards.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "narration", "income.narration.required");
		
		if (obj.getNarration()!=null){
			if (!this.lengthRange(obj.getNarration(), 1, 500)){
				errors.rejectValue("narration", "income.narration.size");
			}
		}
		
		if (obj.getTransactionDate()==null) {
			errors.rejectValue("transactionDate", "income.transactionDate.required");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receiptNumber", "income.receiptNumber.required");
		
		if (obj.getReceiptNumber()!=null){
			if (!this.lengthRange(obj.getReceiptNumber(), 1, 20)){
				errors.rejectValue("receiptNumber", "income.receiptNumber.size");
			}
		}
		
		if (obj.getReceiptDate()==null) {
			errors.rejectValue("receiptDate", "income.receiptDate.required");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receivedFrom", "income.receivedFrom.required");
		
		if (obj.getReceivedFrom()!=null){
			if (!this.lengthRange(obj.getReceivedFrom(), 1, 100)){
				errors.rejectValue("receivedFrom", "income.receivedFrom.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "modeOfReceipt", "income.modeOfReceipt.required");
		
		if (obj.getModeOfReceipt()!=null){
			if (!this.lengthRange(obj.getModeOfReceipt(), 1, 20)){
				errors.rejectValue("modeOfReceipt", "income.modeOfReceipt.size");
			}
		}
		
		if (obj.getHeadOfAccount()==null) {
			errors.rejectValue("headOfAccount", "income.headOfAccount.required");
		}

	}
}
