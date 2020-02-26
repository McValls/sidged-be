package com.mvalls.sidged.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.ClassState;
import com.mvalls.sidged.model.ClassStudentPresent;
import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.model.CourseClass;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.model.StudentPresent;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.model.analytics.Desertor;
import com.mvalls.sidged.repositories.CourseClassRepository;
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
@Service
public class CourseClassService extends GenericService<CourseClass, CourseClassRepository>{
	
	private static final int DESERTOR_AMOUNT_OF_CLASSES = 3;
	
	@Autowired private CourseClassRepository courseClassRepository;
	@Autowired private CourseService courseService;
	
	public CourseClass create(Teacher teacher, CourseClass courseClass) throws UnauthorizedUserException {
		Course course = courseService.findById(courseClass.getCourse().getId());
		
		validateTeacherAndCourse(teacher, course);
		
		Collection<Student> students = new ArrayList<>(course.getStudents());
		Collection<ClassStudentPresent> classStudents = students.stream()
			.map(student -> new ClassStudentPresent(null, courseClass, student, StudentPresent.ABSENT))
			.collect(Collectors.toList());
		courseClass.setCourse(course);
		courseClass.setClassNumber(course.getClasses().size() + 1);
		courseClass.setClassState(ClassState.PENDIENTE);
		courseClass.setStudentPresents(classStudents);
		
		courseClassRepository.save(courseClass);
		
		return courseClass;
	}
	
	@Transactional
	public Collection<CourseClass> findClassesByCourseId(Long courseId){
		Course course = courseService.findById(courseId);
		return course.getClasses();
	}
	
	public Collection<ClassStudentPresent> getClassStudents(Long courseId, Long classId) {
		CourseClass courseClass = courseClassRepository.findByCourseIdAndId(courseId, classId);
		
		return courseClass.getStudentPresents();
	}
	
	public void updateClassState(Teacher teacher, Long courseId, Long classId, ClassState classState) throws UnauthorizedUserException {
		CourseClass courseClass = courseClassRepository.findByCourseIdAndId(courseId, classId);
		if(courseClass != null) {
			validateTeacherAndCourse(teacher, courseClass.getCourse());
			
			courseClass.setClassState(classState);
			update(courseClass);
		}
	}
	
	@Transactional
	/**
	 * Busca las ultimas 3 clases de cada curso y recorre la lista de presentes.
	 * Si hay alumnos que tienen {DESERTOR_AMOUNT_OF_CLASSES} ausentes corridos, los toma como desertores y notifica.
	 * @return
	 */
	public List<Desertor> getDesertors() {
		List<Desertor> desertors = new ArrayList<>();
		Collection<CourseClass> lastClassesOfEachCourse = courseClassRepository.findByCourseYearAndClassStateOrderByCourseIdAscClassNumberDesc(LocalDate.now().getYear(), ClassState.FINALIZADA);
		Map<Course, List<CourseClass>> lastClassesByCourse = lastClassesOfEachCourse.stream().collect(Collectors.groupingBy(classes -> classes.getCourse()));
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

	@Override
	protected CourseClassRepository getRepository() {
		return courseClassRepository;
	}

	@Transactional
	public void updateComments(Long classId, String comments) {
		CourseClass courseClass = findById(classId);
		courseClass.setComments(comments);
		update(courseClass);
	}

	public CourseClass findByTeacherAndId(Teacher teacher, Long classId) throws UnauthorizedUserException {
		CourseClass courseClass = this.findById(classId);
		if(courseClass != null) {
			this.validateTeacherAndCourse(teacher, courseClass.getCourse());
			return courseClass;
		}
		return null;
	}
	
	private void validateTeacherAndCourse(Teacher teacher, Course course) throws UnauthorizedUserException {
		if (! course.getTeachers().contains(teacher)) {
			throw new UnauthorizedUserException();
		}
	}
	
}
