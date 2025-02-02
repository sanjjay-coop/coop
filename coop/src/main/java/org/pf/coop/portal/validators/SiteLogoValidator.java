package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.forms.FileUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SiteLogoValidator extends BaseValidator implements Validator  {

	public static final String PNG_MIME_TYPE="image/png";
	public static final long HUNDRED_KB_IN_BYTES = 102400;
	
	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		FileUploadForm obj = (FileUploadForm) target;
		
		MultipartFile file = obj.getFile();
		
		if(file.isEmpty()){
			errors.rejectValue("file", "fileUploadForm.profile.file.required");
		} else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
			errors.rejectValue("file", "fileUploadForm.profile.file.invalid.type");
		} else if(file.getSize() > HUNDRED_KB_IN_BYTES){
			errors.rejectValue("file", "fileUploadForm.profile.exceeded.file.size");
		}
	}
}
