package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OrderValidator extends BaseValidator implements Validator {

	public static final String PDF_MIME_TYPE="application/pdf";
	public static final long FILE_SIZE_IN_BYTES = 4194304;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Order.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Order obj = (Order) target;
		
		MultipartFile file = obj.getFile();
		
		if (obj.getOrderCategory()==null) {
			errors.rejectValue("orderCategory", "order.orderCategory.required");
		}
		
		if (obj.getOrderDate()==null) {
			errors.rejectValue("orderDate", "order.orderDate.required");
		}
		
		if (obj.getOrderNumber()!=null) {
			if (!this.lengthRange(obj.getOrderNumber(), 0, 100)) {
				errors.rejectValue("orderNumber", "order.orderNumber.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "order.subject.required");
		
		if (obj.getSubject()!=null) {
			if (!this.lengthRange(obj.getSubject(), 1, 500)) {
				errors.rejectValue("subject", "order.subject.size");
			}
		}
		
		if(file.isEmpty()){
            errors.rejectValue("file", "order.file.required");
        }
        else if(!PDF_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
            errors.rejectValue("file", "order.file.type");
        }
       
        else if(file.getSize() > FILE_SIZE_IN_BYTES){
            errors.rejectValue("file", "order.file.size");
        }
	}
}
