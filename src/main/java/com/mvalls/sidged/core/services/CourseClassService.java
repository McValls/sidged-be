package com.mvalls.sidged.core.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;

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
public class CourseClassService {
	
	private final CourseClassRepository courseClassRepository;
	private final CourseRepository courseRepository;
	private final ClassStudentPresentRepository classStudentPresentRepository;
	private final TeacherRepository teacherRepository;
	
	public CourseClassService(CourseClassRepository courseClassRepository,
			CourseRepository courseRepository,
			ClassStudentPresentRepository classStudentPresentRepository,
			TeacherRepository teacherRepository) {
		this.courseClassRepository = courseClassRepository;
		this.courseRepository = courseRepository;
		this.classStudentPresentRepository = classStudentPresentRepository;
		this.teacherRepository = teacherRepository;
	}
	
	public CourseClass create(Teacher teacher, String courseCode, LocalDate date) throws UnauthorizedUserException {
		Course course = this.courseRepository.findByCode(courseCode).orElseThrow();
		if (!course.getTeachers().contains(teacher)) {
			throw new UnauthorizedUserException();
		}
		
		List<ClassStudentPresent> classStudents = course.getStudents().stream()
			.map(student -> ClassStudentPresent.builder()
					.student(student)
					.present(StudentPresent.ABSENT)
					.build())
			.collect(Collectors.toList());
		
		
		CourseClass courseClass = CourseClass.builder()
				.classNumber(course.getClasses().size() + 1)
				.classState(ClassState.PENDIENTE)
				.studentPresents(classStudents)
				.date(date)
				.course(course)
				.build();
		
		this.courseClassRepository.create(courseClass);
		return courseClass;
	}
	
	public Collection<CourseClass> findClassesByCourseCode(String courseCode){
		return this.courseClassRepository.findByCourseCode(courseCode);
	}
	
	public Collection<ClassStudentPresent> getClassStudents(String courseCode, Integer classNumber) {
		return this.classStudentPresentRepository.findByCourseAndClassNumber(courseCode, classNumber);
	}
	
	public void updateClassState(Teacher teacher, String courseCode, Integer classNumber, ClassState classState) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();
		
		Optional<CourseClass> optionalCourseClass = this.courseClassRepository.findByCourseCodeAndClassNumber(courseCode, classNumber);
		CourseClass courseClass = optionalCourseClass.orElseThrow();
		courseClass.setClassState(classState);
		this.courseClassRepository.update(courseClass);
	}

	public CourseClass findByTeacherAndCourseCodeAndClassNumber(Teacher teacher, String courseCode, Integer classNumber) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();
		
		return this.courseClassRepository.findByCourseCodeAndClassNumber(courseCode, classNumber)
				.orElseThrow();
	}
	
}
