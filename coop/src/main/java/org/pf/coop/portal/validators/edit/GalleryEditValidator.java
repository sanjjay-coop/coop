package org.pf.coop.portal.validators.edit;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.forms.FileUploadForm;
import org.pf.coop.portal.model.Gallery;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class GalleryEditValidator extends BaseValidator implements Validator  {
	
	public static final String PNG_MIME_TYPE="image/png";
	public static final String JPG_MIME_TYPE="image/jpg";
	public static final String JPEG_MIME_TYPE="image/jpeg";
	public static final String PDF_MIME_TYPE="application/pdf";
	public static final long SIZE_IN_BYTES = 1097152;

	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Gallery obj = (Gallery) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "gallery.title.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "gallery.title.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "gallery.description.required");
		
		if (obj.getDate()==null) {
			errors.rejectValue("date", "gallery.date.required");
		}
		
		if(!file.isEmpty()){
			if(!((PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())) || 
				(JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType())) || 
				(JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType())))){
				errors.rejectValue("file", "document.file.type");
			} 
			if(file.getSize() > SIZE_IN_BYTES){
				errors.rejectValue("file", "document.file.size");
			}
		}
	}
}