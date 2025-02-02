package org.pf.coop.portal.validators;

import org.pf.coop.common.BaseValidator;
import org.pf.coop.portal.model.Parameters;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ParametersValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return Parameters.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Parameters obj = (Parameters) target;

		if (obj.getSiteUrl() != null) {
			if (!this.lengthRange(obj.getSiteUrl(), 0, 255)) {
				errors.rejectValue("siteUrl", "parameters.siteUrl.size");
			}
		}
		
		if (obj.getMailEnable()==null) {
			errors.rejectValue("mailEnable", "parameters.mailEnable.required");
		}
		
		if (obj.getMailHost() != null) {
			if (!this.lengthRange(obj.getMailHost(), 0, 255)) {
				errors.rejectValue("mailHost", "parameters.mailHost.size");
			}
		}
		
		if (obj.getMailPort() == null) {
			obj.setMailPort(0);
		}
		
		if (obj.getMailUsername() != null) {
			if (!this.lengthRange(obj.getMailUsername(), 0, 255)) {
				errors.rejectValue("mailUsername", "parameters.mailUsername.size");
			}
		}
		
		if (obj.getMailPassword() != null) {
			if (!this.lengthRange(obj.getMailPassword(), 0, 255)) {
				errors.rejectValue("mailPassword", "parameters.mailPassword.size");
			}
		}
		
		if (obj.getMailTransportProtocol() != null) {
			if (!this.lengthRange(obj.getMailTransportProtocol(), 0, 50)) {
				errors.rejectValue("mailTransportProtocol", "parameters.mailTransportProtocol.size");
			}
		}
		
		if (obj.getMailSmtpPort() == null) {
			obj.setMailSmtpPort(0);
		}
		
		if (obj.getMailSmtpAuth() == null) {
			obj.setMailSmtpAuth(false);
		}
		
		if (obj.getMailSmtpStarttlsEnable() == null) {
			obj.setMailSmtpStarttlsEnable(false);
		}
		
		if (obj.getFromEmail() != null) {
			if (!this.lengthRange(obj.getFromEmail(), 0, 255)) {
				errors.rejectValue("fromEmail", "parameters.fromEmail.size");
			}
		}
		
		if (obj.getEmailSignature() != null) {
			if (!this.lengthRange(obj.getEmailSignature(), 0, 255)) {
				errors.rejectValue("emailSignature", "parameters.emailSignature.size");
			}
		}
		
		if (obj.getHeader() != null) {
			if (!this.lengthRange(obj.getHeader(), 0, 100)) {
				errors.rejectValue("header", "parameters.header.size");
			}
		}
		
		if (obj.getSubHeader() != null) {
			if (!this.lengthRange(obj.getSubHeader(), 0, 100)) {
				errors.rejectValue("subHeader", "parameters.subHeader.size");
			}
		}
		
		if (obj.getDataDirectory() != null) {
			if (!this.lengthRange(obj.getDataDirectory(), 0, 255)) {
				errors.rejectValue("dataDirectory", "parameters.dataDirectory.size");
			}
		}
	}
}
