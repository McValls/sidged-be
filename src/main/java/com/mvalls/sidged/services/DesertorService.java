package com.mvalls.sidged.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.analytics.Desertor;
import com.mvalls.sidged.model.emails.Email;

@Service
public class DesertorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DesertorService.class);
	private final CourseClassService courseClassService;
	private final EmailsService emailsService;

	@Autowired
	public DesertorService(CourseClassService courseClassService, EmailsService emailsService) {
		this.courseClassService = courseClassService;
		this.emailsService = emailsService;
	}

	public void getDesertorsAndSendEmail() {
		List<Desertor> desertors = courseClassService.getDesertors();
		LOGGER.info("{} desertors founded. Starting to send mails...", desertors.size());
		desertors.parallelStream().forEach(desertor -> {
			Email desertorEmail = Email.builder()
					.to(desertor.getStudent().getContactData().getDefaultEmail())
					.subject(getSubject(desertor))
					.message(getMessage(desertor))
					.build();
			emailsService.sendEmail(desertorEmail);
		});
		LOGGER.info("Mails sended");
	}
	
	private String getSubject(Desertor desertor) {
		return "Ausencia prolongada a " + desertor.getCourse().getName();
	}
	
	private String getMessage(Desertor desertor) {
		StringBuilder sb = new StringBuilder();
		sb
			.append("Hola ").append(desertor.getStudent().getNames()).append(":\n\n")
			.append("Detectamos que faltaste a las últimas clases de " + desertor.getCourse().getName())
			.append(" y nos gustaría saber más al respecto.\n\n ")
			.append("¡Recordá que nuestros docentes y personal te pueden ayudar a encontrar alternativas para ")
			.append("que no dejes la cursada!");
		
		return sb.toString();
	}

}
