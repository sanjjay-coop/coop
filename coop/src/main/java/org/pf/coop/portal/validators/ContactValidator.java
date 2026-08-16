package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Contact;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ContactValidator extends BaseValidator implements Validator {
	
	public static final String PNG_MIME_TYPE="image/png";
	public static final String JPG_MIME_TYPE="image/jpg";
	public static final String JPEG_MIME_TYPE="image/jpeg";
	public static final long SIZE_IN_BYTES = 102400;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Contact.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Contact obj = (Contact) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "contact.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "contact.address.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "designation", "contact.designation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "contact.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "contact.phone.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "contact.name.size");
			}
		}

		if (obj.getAddress()!=null){
			if (!this.lengthRange(obj.getAddress(), 1, 500)){
				errors.rejectValue("address", "contact.address.size");
			}
		}

		if (obj.getDesignation()!=null){
			if (!this.lengthRange(obj.getDesignation(), 1, 50)){
				errors.rejectValue("designation", "contact.designation.size");
			}
		}

		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 1, 255)){
				errors.rejectValue("email", "contact.email.size");
			}
			
			if (!this.isEmail(obj.getEmail())) {
				errors.rejectValue("email", "contact.email.valid");
			}
		}

		if (obj.getPhone()!=null){
			if (!this.lengthRange(obj.getPhone(), 1, 20)){
				errors.rejectValue("phone", "contact.phone.size");
			}
			
			if (!this.isNumeric(obj.getPhone())) {
				errors.rejectValue("phone", "contact.phone.numeric");
			}
		}
		
		if(file.isEmpty()){
			errors.rejectValue("file", "contact.file.required");
		} else if(!((PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())) || 
				(JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType())) || 
				(JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType())))){
			errors.rejectValue("file", "contact.file.type");
		} else if(file.getSize() > SIZE_IN_BYTES){
			errors.rejectValue("file", "contact.file.size");
		}
	}
}
