package com.mvalls.sidged.core.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.analytics.Desertor;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.repositories.StudentRepository;
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
public class CourseClassService extends GenericService<CourseClass, CourseClassRepository>{
	

	private static final int DESERTOR_AMOUNT_OF_CLASSES = 3;
	
	private final CourseRepository courseRepository;
	private final ClassStudentPresentRepository classStudentPresentRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;
	
	
	public CourseClassService(CourseClassRepository repository,
			CourseRepository courseRepository,
			ClassStudentPresentRepository classStudentPresentRepository,
			TeacherRepository teacherRepository,
			StudentRepository studentRepository) {
		super(repository);
		this.courseRepository = courseRepository;
		this.classStudentPresentRepository = classStudentPresentRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}
	
	@Deprecated
	public CourseClass create(Teacher teacher, String courseCode, LocalDate date) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) {
			throw new UnauthorizedUserException();
		}
		
		Course course = this.courseRepository.findByCode(courseCode).orElseThrow();
		List<CourseClass> previousClasses = this.repository.findByCourseCode(courseCode);
		List<Student> students = this.studentRepository.findByCourseCode(courseCode);
		
		List<ClassStudentPresent> classStudents = students.stream()
			.map(student -> ClassStudentPresent.builder()
					.student(student)
					.present(StudentPresent.ABSENT)
					.build())
			.collect(Collectors.toList());
		
		
		CourseClass courseClass = CourseClass.builder()
				.classNumber(previousClasses.size() + 1)
				.classState(ClassState.PENDIENTE)
				.studentPresents(classStudents)
				.date(date)
				.course(course)
				.build();
		
		this.repository.create(courseClass);
		return courseClass;
	}
	
	public Collection<CourseClass> findClassesByCourseCode(String courseCode){
		return this.repository.findByCourseCode(courseCode);
	}
	
	//TODO: Refactor!!
	public Collection<ClassStudentPresent> getClassStudents(String courseCode, Integer classNumber) {
		return this.classStudentPresentRepository.findByCourseAndClassNumber(courseCode, classNumber);
	}
	
	public void updateClassState(Teacher teacher, String courseCode, Integer classNumber, ClassState classState) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();
		
		Optional<CourseClass> optionalCourseClass = this.repository.findByCourseCodeAndClassNumber(courseCode, classNumber);
		CourseClass courseClass = optionalCourseClass.orElseThrow();
		courseClass.setClassState(classState);
		this.repository.update(courseClass);
	}
	
	/**
	 * Busca las ultimas 3 clases de cada curso y recorre la lista de presentes.
	 * Si hay alumnos que tienen {DESERTOR_AMOUNT_OF_CLASSES} ausentes corridos, los toma como desertores y notifica.
	 * @return
	 */
	@Deprecated
	//TODO: Mover a DesertorService
	public List<Desertor> getDesertors() {
		List<Desertor> desertors = new ArrayList<>();
//		List<Course> coursesByYear = this.courseRepository.findByYear(LocalDate.now().getYear());
//		
//		Map<Course, List<CourseClass>> lastClassesByCourse = coursesByYear.stream()
//			.collect(Collectors.toMap(
//					course -> course,
//					course -> course.getClasses()
//						.stream()
//						.filter(cc -> cc.getClassState() == ClassState.FINALIZADA)
//						.sorted((cc1, cc2) -> cc1.getClassNumber().compareTo(cc2.getClassNumber()))
//						.collect(Collectors.toList())
//					));
//		
//		lastClassesByCourse.forEach((course, listOfClasses) -> {
//			if(listOfClasses.size() >= DESERTOR_AMOUNT_OF_CLASSES) {
//				List<CourseClass> lastClasses = listOfClasses.subList(0, DESERTOR_AMOUNT_OF_CLASSES);
//				Map<Student, Integer> absentsByStudent = new HashMap<>();
//				lastClasses.forEach(courseClass -> {
//					courseClass.getStudentPresents().forEach(classStudentPresent -> {
//						addAbsentByStudent(absentsByStudent, classStudentPresent);
//					});
//				});
//	
//				absentsByStudent.forEach((student, absents) -> {
//					if(absents.compareTo(DESERTOR_AMOUNT_OF_CLASSES) >= 0) {
//						Desertor desertor = Desertor.builder()
//								.course(course)
//								.student(student)
//								.localDate(LocalDate.now())
//								.build();
//						
//						desertors.add(desertor);
//					}
//				});
//			}
//			
//		});
//		
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

	//TODO: refactor!
	public CourseClass findByTeacherAndCourseCodeAndClassNumber(Teacher teacher, String courseCode, Integer classNumber) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();
		
		return this.repository.findByCourseCodeAndClassNumber(courseCode, classNumber)
				.orElseThrow();
	}
	
}
