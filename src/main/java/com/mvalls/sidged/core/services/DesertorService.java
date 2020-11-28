package com.mvalls.sidged.core.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.analytics.Desertor;
import com.mvalls.sidged.core.model.emails.Email;
import com.mvalls.sidged.core.repositories.CourseRepository;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class DesertorService {
	
	private static final int DESERTOR_AMOUNT_OF_CLASSES = 3;
	
	private final EmailsService emailsService;
	private final CourseRepository courseRepository;
	private final NotificationService notificationService;
	
	public DesertorService(EmailsService emailsService, 
			NotificationService notificationService, 
			CourseRepository courseRepository) {
		super();
		this.emailsService = emailsService;
		this.notificationService = notificationService;
		this.courseRepository = courseRepository;
	}

	public void getDesertorsAndSendEmail() {
		List<Desertor> desertors = this.getDesertors();
		log.info("{} desertors founded. Starting to send mails...", desertors.size());
		desertors.parallelStream().forEach(this::sendEmail);
		this.notificationService.notifyInstution(desertors);
		log.info("Mails sended");
	}
	
	/**
	 * Busca las ultimas 3 clases de cada curso y recorre la lista de presentes.
	 * Si hay alumnos que tienen {DESERTOR_AMOUNT_OF_CLASSES} ausentes corridos, los toma como desertores y notifica.
	 * @return
	 */

	private List<Desertor> getDesertors() {
		List<Desertor> desertors = new ArrayList<>();
		List<Course> coursesByYear = this.courseRepository.findByYear(LocalDate.now().getYear());
		
		Map<Course, List<CourseClass>> lastClassesByCourse = coursesByYear.stream()
			.collect(Collectors.toMap(
					course -> course,
					course -> course.getClasses()
						.stream()
						.filter(cc -> cc.getClassState() == ClassState.FINALIZADA)
						.sorted((cc1, cc2) -> cc1.getClassNumber().compareTo(cc2.getClassNumber()))
						.collect(Collectors.toList())
					));
		
		lastClassesByCourse.forEach((course, listOfClasses) -> {
			if(listOfClasses.size() >= DESERTOR_AMOUNT_OF_CLASSES) {
				List<CourseClass> lastClasses = listOfClasses.subList(0, DESERTOR_AMOUNT_OF_CLASSES);
				Map<Student, Integer> absentsByStudent = new HashMap<>();
				lastClasses.forEach(courseClass -> {
					courseClass.getStudentPresents().forEach(classStudentPresent -> {
						addAbsentByStudent(absentsByStudent, classStudentPresent);
					});
				});
	
				absentsByStudent.forEach((student, absents) -> {
					if(absents.compareTo(DESERTOR_AMOUNT_OF_CLASSES) >= 0) {
						Desertor desertor = Desertor.builder()
								.course(course)
								.student(student)
								.localDate(LocalDate.now())
								.build();
						
						desertors.add(desertor);
					}
				});
			}
			
		});
		
		return desertors;
	}
	
	private void addAbsentByStudent(Map<Student, Integer> absentsByStudent, ClassStudentPresent classStudentPresent) {
		if(!absentsByStudent.containsKey(classStudentPresent.getStudent())) {
			absentsByStudent.put(classStudentPresent.getStudent(), 0);
		}
		if(classStudentPresent.getPresent() == StudentPresent.ABSENT) {
			absentsByStudent.put(classStudentPresent.getStudent(), absentsByStudent.get(classStudentPresent.getStudent()) + 1);
		}
	}
	
	private void sendEmail(Desertor desertor) {
		Email desertorEmail = Email.builder()
				.to(desertor.getStudent().getContactData().getDefaultEmail())
				.subject(getSubject(desertor))
				.message(getMessage(desertor))
				.build();
		emailsService.sendEmail(desertorEmail);
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
