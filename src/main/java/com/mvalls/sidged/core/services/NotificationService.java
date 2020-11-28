package com.mvalls.sidged.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.analytics.Desertor;
import com.mvalls.sidged.core.model.emails.Email;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;

public class NotificationService {
	
	private final EmailsService emailService;
	private final CourseRepository courseRepository;
	private final String institutionEmail;
	private final String desertorsSubject;
	
	public NotificationService(EmailsService emailService, 
			CourseRepository courseRepository,
			String institutionEmail,
			String desertorsSubject) {
		super();
		this.emailService = emailService;
		this.courseRepository = courseRepository;
		this.institutionEmail = institutionEmail;
		this.desertorsSubject = desertorsSubject;
	}
	
	public void sendEmailToStudents(String courseCode, Teacher teacher, String subject, String message) throws UnauthorizedUserException {
		Course course = this.courseRepository.findByCode(courseCode).orElseThrow();
		Set<Teacher> teachersByCourse = course.getTeachers();
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();

		Set<Student> students = course.getStudents();
		students.parallelStream().forEach(student -> this.sendEmail(course, student, subject, message));
	}

	private void sendEmail(Course course, Student student, String subject, String message) {
		subject = course.getName() + " " + course.getYear() + ": " + subject;
		Email email = Email.builder().to(student.getContactData().getDefaultEmail()).subject(subject).message(message)
				.build();
		this.emailService.sendEmail(email);
	}
	
	public void notifyInstution(List<Desertor> desertors) {
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
			.to(this.institutionEmail)
			.subject(this.desertorsSubject)
			.message(sb.toString())
			.build();
		
		emailService.sendEmail(email);
	}
	
	private String getDesertorCourseAndNames(Desertor desertor) {
		Course course = desertor.getCourse();
		Student student = desertor.getStudent();
		return course.getName() + " " + course.getYear() + " - " + student.getNames() + " " + student.getLastname(); 
	}

}
