package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Carousel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CarouselValidator extends BaseValidator implements Validator  {

	public static final String PNG_MIME_TYPE="image/png";
	public static final String JPG_MIME_TYPE="image/jpg";
	public static final String JPEG_MIME_TYPE="image/jpeg";
	public static final long SIZE_IN_BYTES = 2097152;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Carousel.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Carousel obj = (Carousel) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmpty(errors, "title", "carousel.title.required");
		ValidationUtils.rejectIfEmpty(errors, "description", "carousel.description.required");
		
		if (obj.getTitle()!=null) {
			if (!this.lengthRange(obj.getTitle(), 1,50)) {
				errors.rejectValue("title", "carousel.title.size");
			}
		}
		
		if (obj.getDescription()!=null) {
			if (!this.lengthRange(obj.getDescription(), 1,200)) {
				errors.rejectValue("description", "carousel.description.size");
			}
		}
		
		if (obj.getPubEndDate()==null) {
			errors.rejectValue("pubEndDate", "carousel.pubEndDate.required");
		}
		
		if(file.isEmpty()){
			errors.rejectValue("file", "carousel.file.required");
		} else if(!(PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType()))){
			errors.rejectValue("file", "carousel.file.type");
		} else if(file.getSize() > SIZE_IN_BYTES){
			errors.rejectValue("file", "carousel.file.size");
		}
	}
}
