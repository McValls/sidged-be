package com.mvalls.sidged.services;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.model.analytics.Desertor;
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
public class DesertorService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DesertorService.class);
	private final CourseClassService courseClassService;
	private final EmailsService emailsService;
	private final Environment env;

	@Autowired
	public DesertorService(CourseClassService courseClassService, EmailsService emailsService, Environment env) {
		this.courseClassService = courseClassService;
		this.emailsService = emailsService;
		this.env = env;
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
		notifyInstution(desertors);
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
	
	private void notifyInstution(List<Desertor> desertors) {
		desertors.sort((d1, d2) -> d1.getCourse().getName().compareTo(d2.getCourse().getName()));
		
		StringBuilder sb = new StringBuilder();
		sb.append("Reporte de desertores ").append(LocalDate.now().toString()).append(":").append("\n\n");
		
		if(desertors.isEmpty()) {
			sb.append("No se registraron desertores esta vuelta.");
		} else {
			desertors.forEach(desertor -> sb.append(getDesertorCourseAndNames(desertor)).append("\n") );
			sb.append("\n").append("Todos los alumnos han sido notificados.");
		}
		
		Email email = Email.builder()
			.to(env.getProperty("email.institution.account"))
			.subject(env.getProperty("email.desertors.subject"))
			.message(sb.toString())
			.build();
		
		emailsService.sendEmail(email);
	}
	
	private String getDesertorCourseAndNames(Desertor desertor) {
		Course course = desertor.getCourse();
		Student student = desertor.getStudent();
		return course.getName() + " " + course.getYear() + " - " + student.getNames() + " " + student.getLastname(); 
	}

}
