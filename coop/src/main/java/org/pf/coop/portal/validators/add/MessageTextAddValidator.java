package org.pf.coop.portal.validators.add;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MessageTextAddValidator extends BaseValidator implements Validator {

	@Autowired
	private MessageTextRepo messageTextRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return MessageText.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MessageText obj = (MessageText) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "messageFor", "messageText.messageFor.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "messageText.subject.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "messageText.content.required");
		
		if (obj.getMessageFor()!=null){
			if (!this.lengthRange(obj.getMessageFor(), 1, 50)){
				errors.rejectValue("messageFor", "category.messageFor.size");
			}
			
			MessageText o = this.messageTextRepo.findByMessageFor(obj.getMessageFor());
			if (o!=null) {
				errors.rejectValue("messageFor", "messageText.messageFor.unique");
			}
		}

		if (obj.getSubject()!=null){
			if (!this.lengthRange(obj.getSubject(), 1, 255)){
				errors.rejectValue("subject", "messageText.subject.size");
			}
			
			MessageText o = this.messageTextRepo.findBySubject(obj.getSubject());
			if (o!=null) {
				errors.rejectValue("subject", "messageText.subject.unique");
			}
		}
		
		if (obj.getContent()!=null) {
			if (!this.lengthRange(obj.getContent(), 0, 2000)) {
				errors.rejectValue("content", "messageText.content.size");
			}
		}
	}
}
