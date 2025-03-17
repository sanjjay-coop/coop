package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Business;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BusinessFileValidator extends BaseValidator implements Validator {
	
	public static final String PNG_MIME_TYPE="image/png";
	public static final long FILE_SIZE_IN_BYTES = 204800;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Business.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Business obj = (Business) target;
		
		MultipartFile file = obj.getFile();
		
        if(file.isEmpty()){
            errors.rejectValue("file", "fileUploadForm.file.required");
        } else {
        	if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
        		errors.rejectValue("file", "fileUploadForm.file.invalid.type.png");
        	}
        
	        if(file.getSize() > FILE_SIZE_IN_BYTES){
	            errors.rejectValue("file", "fileUploadForm.exceeded.file.size");
	        }
        }
	}
}
