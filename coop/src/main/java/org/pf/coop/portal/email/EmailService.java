package org.pf.coop.portal.email;

import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private EmailConfiguration emailConfiguration;

	@Autowired
	private ParametersService parametersService;

	private void sendEmailMessage(String emailTo, String subject, String txtMessage) {

		SimpleMailMessage message = new SimpleMailMessage();

		Parameters params = parametersService.getParameters();
		
		if (params.getMailEnable()) {					
			message.setFrom(params.getFromEmail());
			message.setTo(emailTo);
			message.setSubject(subject);
	
			String msgText = txtMessage
					+ "\n\n" + params.getEmailSignature();
	
			message.setText(msgText);
	
			try {
				emailConfiguration.getJavaMailSender().send(message);
			} catch (Exception e) {
				System.out.println("Email Error: " + e.getMessage());
			}
		}
	}

	public void sendEmail(String emailTo, String subject, String message) {
		Runnable myrunnable = new Runnable() {
		    @Override
			public void run() {
		        sendEmailMessage(emailTo, subject, message); //Call your function
		    }
		};
		new Thread(myrunnable).start();
	}
	
	// Bulletin (Monthly)

	private void sendEmailMessage(String emails, String bulletin) {

		SimpleMailMessage message = new SimpleMailMessage();

		Parameters params = parametersService.getParameters();
		
		if (params.getMailEnable()) {					
			message.setFrom(params.getFromEmail());
			message.setTo(params.getFromEmail());
			message.setBcc(emails.split(", "));
			message.setSubject("Parivrajaka Foundation: Monthly Bulletin");
	
			String msgText = bulletin
					+ "\n\n" + params.getEmailSignature();
	
			message.setText(msgText);
	
			try {
				emailConfiguration.getJavaMailSender().send(message);
			} catch (Exception e) {
				System.out.println("Email Error: " + e.getMessage());
			}
		}
	}

	public void sendEmail(String emails, String bulletin) {
		Runnable myrunnable = new Runnable() {
		    @Override
			public void run() {
		        sendEmailMessage(emails, bulletin); //Call your function
		    }
		};
		new Thread(myrunnable).start();
	}
	
	// Bulk Messaging

	private void sendEmailMessage(BulkEmail bulkEmail) {

		SimpleMailMessage message = new SimpleMailMessage();

		Parameters params = parametersService.getParameters();
		
		if (params.getMailEnable()) {					
			message.setFrom(params.getFromEmail());
			message.setTo(params.getFromEmail());
			message.setBcc(bulkEmail.getBcc().split(", "));
			message.setSubject(bulkEmail.getSubject());
	
			String msgText = bulkEmail.getMessage()
					+ "\n\n" + params.getEmailSignature();
	
			message.setText(msgText);
	
			try {
				emailConfiguration.getJavaMailSender().send(message);
			} catch (Exception e) {
				System.out.println("Email Error: " + e.getMessage());
			}
		}
	}

	public void sendEmail(BulkEmail bulkEmail) {
		Runnable myrunnable = new Runnable() {
		    @Override
			public void run() {
		        sendEmailMessage(bulkEmail); //Call your function
		    }
		};
		new Thread(myrunnable).start();
	}
}
