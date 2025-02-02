package org.pf.coop.portal.email;

import java.util.Properties;

import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailConfiguration {
	
	@Autowired
	private ParametersService parametersService;
	
    public JavaMailSender getJavaMailSender() {
		
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
		Parameters params = this.parametersService.getParameters();
		
		if (params!=null && params.getMailEnable()) {
	        
			mailSender.setHost(params.getMailHost());
		    mailSender.setPort(params.getMailPort());
		       
		    mailSender.setUsername(params.getMailUsername());
		    mailSender.setPassword(params.getMailPassword());
		        
		    Properties props = mailSender.getJavaMailProperties();
		        
		    props.put("mail.transport.protocol", params.getMailTransportProtocol());
		    props.put("mail.smtp.auth", params.getMailSmtpAuth());
		    props.put("mail.smtp.starttls.enable", params.getMailSmtpStarttlsEnable());
		    props.put("mail.smtp.port", params.getMailSmtpPort());
		    props.put("mail.debug", "true");
			
		}
        
        return mailSender;
    }
}
