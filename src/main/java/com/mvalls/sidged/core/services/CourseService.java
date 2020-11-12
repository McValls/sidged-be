package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.List;

import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.enums.UpdateAction;
import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.Time;
import com.mvalls.sidged.core.model.analytics.CoursePresentismData;
import com.mvalls.sidged.core.model.analytics.PresentPercentages;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.model.emails.Email;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.strategies.actions.UpdateActionStrategy;
import com.mvalls.sidged.core.strategies.actions.students.StudentUpdateActionStrategyFactory;
import com.mvalls.sidged.core.strategies.actions.teachers.TeacherUpdateActionStrategyFactory;
import com.mvalls.sidged.mappers.CourseVOtoModelMapper;
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
	private final TeacherService teacherService;
	private final StudentService studentService;
	private final TimeService timeService;
	private final PeriodService periodService;
	private final CourseVOtoModelMapper courseVOtoModelMapper;
	private final PresentAnalysisCalculator presentAnalysisCalculator;
	private final EmailsService emailsService;
	private final CareerRepository careerRepository;

	public CourseService(CourseRepository repository, CareerRepository careerRepository, TeacherService teacherService, StudentService studentService,
			TimeService timeService, PeriodService periodService,
			CourseVOtoModelMapper courseVOtoModelMapper, PresentAnalysisCalculator presentAnalysisCalculator,
			EmailsService emailsService) {
		super(repository);
		this.careerRepository = careerRepository;
		this.teacherService = teacherService;
		this.studentService = studentService;
		this.timeService = timeService;
		this.periodService = periodService;
		this.courseVOtoModelMapper = courseVOtoModelMapper;
		this.presentAnalysisCalculator = presentAnalysisCalculator;
		this.emailsService = emailsService;
	}

	public Collection<Student> findStudentsByCourseCode(String code) {
		return this.repository.findByCode(code).getStudents();
	}

	public Collection<Teacher> findTeachersByCourseCode(String code) {
		return this.repository.findByCode(code).getTeachers();
	}

	public Collection<Teacher> updateTeacher(String courseCode, Teacher teacher, UpdateAction action) {
		Course course = this.repository.findByCode(courseCode);
		teacher = teacherService.findById(teacher.getId());
		Collection<Teacher> teachersOfTheCourse = course.getTeachers();
		UpdateActionStrategy<Teacher> updateStrategy = TeacherUpdateActionStrategyFactory.getInstance(action);
		updateStrategy.execute(teacher, teachersOfTheCourse);

		this.update(course);

		return teachersOfTheCourse;
	}

	public Collection<Student> updateStudent(String courseCode, Student student, UpdateAction action) {
		Course course = this.repository.findByCode(courseCode);
		student = studentService.findById(student.getId());
		Collection<Student> studentsOfTheCourse = course.getStudents();
		UpdateActionStrategy<Student> updateStrategy = StudentUpdateActionStrategyFactory.getInstance(action);
		updateStrategy.execute(student, studentsOfTheCourse);

		this.update(course);

		return studentsOfTheCourse;
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

	public CoursePresentismData getPresentismByCourseGroupedByMonth(String courseCode) {
		Course course = this.repository.findByCode(courseCode);
		return presentAnalysisCalculator.getPresentismByCourseGroupedByMonth(course);
	}

	public PresentismAnalysisData getPresentismAnalysis(String courseCode) {
		Course course = this.repository.findByCode(courseCode);
		List<PresentPercentages> presentPercentagesByClasses = presentAnalysisCalculator
				.getPresentPercentagesByClasses(course);

		PresentismAnalysisData analysisData = new PresentismAnalysisData();
		analysisData.setPercentagesByClassNumber(presentPercentagesByClasses);
		analysisData.setCourseCode(courseCode);
		analysisData.setCourseName(course.getName());
		return analysisData;
	}

	public Collection<Course> findByTeacher(Long teacherId) {
		return this.repository.findByTeachersId(teacherId);
	}

	public Collection<Course> findByStudent(Long studentId) {
		return this.repository.findByStudentsId(studentId);
	}

	public void sendEmailToStudents(String courseCode, Long teacherId, String subject, String message) {
		Course course = this.repository.findByCode(courseCode);
		if (course.getTeachers().stream().noneMatch(teacher -> teacher.getId().equals(teacherId))) {
			throw new IllegalArgumentException("This teacher has no control over this course!");
		}

		course.getStudents().parallelStream().forEach(student -> this.sendEmail(course, student, subject, message));
	}

	private void sendEmail(Course course, Student student, String subject, String message) {
		subject = course.getName() + " " + course.getYear() + ": " + subject;
		Email email = Email.builder().to(student.getContactData().getDefaultEmail()).subject(subject).message(message)
				.build();
		this.emailsService.sendEmail(email);
	}

}
