package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Receipt;
import org.pf.coop.portal.repository.ReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReceiptValidator extends BaseValidator implements Validator {

	public static final String PNG_MIME_TYPE="image/png";
	public static final long HUNDRED_KB_IN_BYTES = 102400;
	
	@Autowired
	private ReceiptRepo receiptRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Receipt.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Receipt obj = (Receipt) target;
		
		MultipartFile file = obj.getFile();
		
		if (obj.getMember()==null) {
			errors.rejectValue("member", "receipt.member.required");
		}
		
		if (obj.getReceiptDate()==null) {
			errors.rejectValue("receiptDate", "receipt.receiptDate.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receiptNumber", "receipt.receiptNumber.required");
		
		if (obj.getReceiptNumber()!=null) {
			if (!this.lengthRange(obj.getReceiptNumber(), 1, 100)) {
				errors.rejectValue("receiptNumber", "receipt.receiptNumber.size");
			}
			
			Receipt o = this.receiptRepo.findByReceiptNumberIgnoreCase(obj.getReceiptNumber());
			
			if (o != null) {
				errors.rejectValue("receiptNumber", "receipt.receiptNumber.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "details", "receipt.details.required");
		
		if (obj.getDetails()!=null) {
			if (!this.lengthRange(obj.getDetails(), 1, 500)) {
				errors.rejectValue("details", "receipt.details.size");
			}
		}
		
		if(file.isEmpty()){
            errors.rejectValue("file", "receipt.file.required");
        }
        else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
            errors.rejectValue("file", "receipt.file.invalid.type");
        }
       
        else if(file.getSize() > HUNDRED_KB_IN_BYTES){
            errors.rejectValue("file", "receipt.exceeded.file.size");
        }
	}
}
