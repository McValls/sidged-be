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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.emails.Email;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
@Service
@PropertySource("classpath:email.properties")
public class EmailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailsService.class);
	
	@Autowired private Environment env;
	
	public void sendEmail(Email email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", env.getProperty("mail.smtp.host"));
		props.put("mail.smtp.port", env.getProperty("mail.smtp.port"));
		props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth", "true"));
		props.put("mail.smtp.socketFactory.port", env.getProperty("mail.smtp.socketFactory.port", "465"));
        props.put("mail.smtp.socketFactory.class", env.getProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"));
		
		
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(env.getProperty("email.account"), env.getProperty("email.secured.password"));
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(env.getProperty("email.from")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
			message.setSubject(email.getSubject());
			message.setText(email.getMessage());
			Transport.send(message);
			
			LOGGER.info("Mail sended to {} ({}) with subject {}", email.getTo(), LocalDateTime.now(), email.getSubject());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRecoveryPasswordEmail(String toEmail, String newPassword) {
		Email email = Email.builder()
				.to(toEmail)
				.subject("Recuperación de contraseña")
				.message("Su contraseña ha sido restaurada. Para ingresar, pruebe con \n" + newPassword + "\n\nNo olvide cambiar su contraseña una vez ingresado.")
				.build();
		
		this.sendEmail(email);
	}
	
}
