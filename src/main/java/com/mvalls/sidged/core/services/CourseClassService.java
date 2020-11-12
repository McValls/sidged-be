package com.mvalls.sidged.core.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.analytics.Desertor;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
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
public class CourseClassService extends GenericService<CourseClass, CourseClassRepository>{
	

	private static final int DESERTOR_AMOUNT_OF_CLASSES = 3;
	
	private final CourseRepository courseRepository;
	
	
	public CourseClassService(CourseClassRepository repository,
			CourseRepository courseRepository) {
		super(repository);
		this.courseRepository = courseRepository;
	}
	
	public CourseClass create(Teacher teacher, String courseCode, LocalDate date) throws UnauthorizedUserException {
		Course course = this.courseRepository.findByCode(courseCode);
		
		validateTeacherAndCourse(teacher, course);
		
		Collection<Student> students = new ArrayList<>(course.getStudents());
		Collection<ClassStudentPresent> classStudents = students.stream()
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
				.build();
		
		course.getClasses().add(courseClass);
		this.courseRepository.update(course);
		
		return courseClass;
	}
	
	@Transactional
	public Collection<CourseClass> findClassesByCourseCode(String courseCode){
		Course course = this.courseRepository.findByCode(courseCode);
		return course.getClasses();
	}
	
	public Collection<ClassStudentPresent> getClassStudents(String courseCode, Integer classNumber) {
		Course course = this.courseRepository.findByCode(courseCode);
		CourseClass courseClass = course.getClasses()
				.stream()
				.filter(c -> c.getClassNumber().equals(classNumber))
				.findFirst()
				.orElseThrow();
		
		return courseClass.getStudentPresents();
	}
	
	public void updateClassState(Teacher teacher, String courseCode, Integer classNumber, ClassState classState) throws UnauthorizedUserException {
		Course course = this.courseRepository.findByCode(courseCode);
		validateTeacherAndCourse(teacher, course);
		
		CourseClass courseClass = course.getClasses()
				.stream()
				.filter(c -> c.getClassNumber().equals(classNumber))
				.findFirst()
				.orElseThrow();
		courseClass.setClassState(classState);
	
		this.repository.update(courseClass);
	}
	
	/**
	 * Busca las ultimas 3 clases de cada curso y recorre la lista de presentes.
	 * Si hay alumnos que tienen {DESERTOR_AMOUNT_OF_CLASSES} ausentes corridos, los toma como desertores y notifica.
	 * @return
	 */
	public List<Desertor> getDesertors() {
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

	public void updateComments(Long classId, String comments) {
		CourseClass courseClass = findById(classId);
		courseClass.setComments(comments);
		update(courseClass);
	}

	public CourseClass findByTeacherAndCourseCodeAndClassNumber(Teacher teacher, String courseCode, Integer classNumber) throws UnauthorizedUserException {
		Course course = this.courseRepository.findByCode(courseCode);
		this.validateTeacherAndCourse(teacher, course);
		return course.getClasses()
				.stream()
				.filter(courseClass -> courseClass.getClassNumber().equals(classNumber))
				.findFirst()
				.orElse(null);
	}
	
	private void validateTeacherAndCourse(Teacher teacher, Course course) throws UnauthorizedUserException {
		if (!course.getTeachers().contains(teacher)) {
			throw new UnauthorizedUserException();
		}
	}
	
}
