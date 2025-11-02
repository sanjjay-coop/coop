package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Document;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DocumentValidator extends BaseValidator implements Validator {

	public static final String PDF_MIME_TYPE="application/pdf";
	public static final long FILE_SIZE_IN_BYTES = 4194304;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Document.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Document obj = (Document) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "document.title.required");
		
		if (obj.getTitle()!=null) {
			if (!this.lengthRange(obj.getTitle(), 1, 500)) {
				errors.rejectValue("title", "document.title.size");
			}
		}
		
		if(file.isEmpty()){
            errors.rejectValue("file", "document.file.required");
        }
        else if(!PDF_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
            errors.rejectValue("file", "document.file.type");
        }
       
        else if(file.getSize() > FILE_SIZE_IN_BYTES){
            errors.rejectValue("file", "document.file.size");
        }
	}
}
