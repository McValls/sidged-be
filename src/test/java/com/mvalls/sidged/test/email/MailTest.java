package com.mvalls.sidged.test.email;

import com.mvalls.sidged.model.emails.Email;
import com.mvalls.sidged.services.EmailsService;

public class MailTest {
	
	public static void main(String[] args) {
		EmailsService emailsService = new EmailsService();
		emailsService.sendEmail(Email.builder()
				.to("cmarcelovalls@gmail.com")
				.message("Holis, prueba")
				.subject("Prueba SUBJECT")
				.build());
	}

}
