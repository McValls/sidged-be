package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.List;

import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.Time;
import com.mvalls.sidged.core.model.analytics.PresentPercentages;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.model.emails.Email;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.mappers.CourseVOtoModelMapper;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.valueObjects.CourseVO;

/**
 * 
 * @author Marcelo Valls
 * 
 *         This file is part of SIDGED-Backend.
 * 
 *         SIDGED-Backend is free software: you can redistribute it and/or
 *         modify it under the terms of the GNU General Public License as
 *         published by the Free Software Foundation, either version 3 of the
 *         License, or (at your option) any later version.
 * 
 *         SIDGED-Backend is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with SIDGED-Backend. If not, see
 *         <https://www.gnu.org/licenses/>.
 *
 */
public class CourseService extends GenericService<Course, CourseRepository> {

	//TODO: Modularizar clase, tiene muchas dependencias
	private final TimeService timeService;
	private final PeriodService periodService;
	private final CourseVOtoModelMapper courseVOtoModelMapper;
	private final PresentAnalysisCalculator presentAnalysisCalculator;
	private final EmailsService emailsService;
	private final CareerRepository careerRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;

	public CourseService(CourseRepository repository, TimeService timeService, PeriodService periodService,
			CourseVOtoModelMapper courseVOtoModelMapper, PresentAnalysisCalculator presentAnalysisCalculator,
			EmailsService emailsService, CareerRepository careerRepository, TeacherRepository teacherRepository,
			StudentRepository studentRepository) {
		super(repository);
		this.timeService = timeService;
		this.periodService = periodService;
		this.courseVOtoModelMapper = courseVOtoModelMapper;
		this.presentAnalysisCalculator = presentAnalysisCalculator;
		this.emailsService = emailsService;
		this.careerRepository = careerRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}

	public void createCourse(CourseVO courseVO) {
		Career career = this.careerRepository.findByCode(courseVO.getCareerCode());
		Course course = courseVOtoModelMapper.map(courseVO);
		course.setCareer(career);
		Time timeSince = timeService.findById(courseVO.getTimeSinceId());
		Time timeUntil = timeService.findById(courseVO.getTimeUntilId());
		Period period = periodService.findByTypeAndNumber(courseVO.getPeriodType(), courseVO.getPeriodNumber());

		course.setTimeStart(timeSince);
		course.setTimeEnd(timeUntil);
		course.setPeriod(period);

		this.repository.create(course);
	}

	public PresentismAnalysisData getPresentismAnalysis(String courseCode) {
		Course course = this.repository.findByCode(courseCode).orElseThrow();
		List<PresentPercentages> presentPercentagesByClasses = presentAnalysisCalculator
				.getPresentPercentagesByClasses(course);

		PresentismAnalysisData analysisData = new PresentismAnalysisData();
		analysisData.setPercentagesByClassNumber(presentPercentagesByClasses);
		analysisData.setCourseCode(courseCode);
		analysisData.setCourseName(course.getName());
		return analysisData;
	}

	public Collection<Course> findByTeacher(Long teacherId) {
		return this.repository.findByTeacherId(teacherId);
	}

	public Collection<Course> findByStudent(Long studentId) {
		return this.repository.findByStudentsId(studentId);
	}

	public void sendEmailToStudents(String courseCode, Teacher teacher, String subject, String message) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();

		Course course = this.repository.findByCode(courseCode).orElseThrow();
		List<Student> students = this.studentRepository.findByCourseCode(courseCode);
		students.parallelStream().forEach(student -> this.sendEmail(course, student, subject, message));
	}

	private void sendEmail(Course course, Student student, String subject, String message) {
		subject = course.getName() + " " + course.getYear() + ": " + subject;
		Email email = Email.builder().to(student.getContactData().getDefaultEmail()).subject(subject).message(message)
				.build();
		this.emailsService.sendEmail(email);
	}

}
