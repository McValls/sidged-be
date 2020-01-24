package com.mvalls.sidged.services;

import java.time.LocalDateTime;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.emails.Email;

@Service
public class EmailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailsService.class);
	
	public void sendEmail(Email email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cmarcelovalls@gmail.com", "zajfzolxxugcagyp");
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("INSPT"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
			message.setSubject(email.getSubject());
			message.setText(email.getMessage());
			Transport.send(message);
			
			LOGGER.info("Mail sended to {} ({}) with subject {}", email.getTo(), LocalDateTime.now(), email.getSubject());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
