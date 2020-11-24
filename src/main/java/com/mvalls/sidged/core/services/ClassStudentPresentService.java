package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
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
public class ClassStudentPresentService extends GenericService<ClassStudentPresent, ClassStudentPresentRepository>{

	private final PresentAnalysisCalculator presentAnalysisCalculator;
	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;
	
	public ClassStudentPresentService(ClassStudentPresentRepository repository,
			CourseRepository courseRepository,
			TeacherRepository teacherRepository,
			PresentAnalysisCalculator presentAnalysisCalculator) {
		super(repository);
		this.presentAnalysisCalculator = presentAnalysisCalculator;
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
	}

	public void updatePresent(Teacher teacher, String courseCode, Integer classNumber, Long studentId, StudentPresent present) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();
		
		Optional<ClassStudentPresent> classStudentPresent = 
				this.repository.findByCourseCodeAndClassNumberAndStudentId(courseCode, classNumber, studentId);
		
		classStudentPresent.ifPresent(studentPresent -> {
			studentPresent.setPresent(present);
			this.repository.update(studentPresent);
		});
	}
	
	public List<PresentismAnalysisData> getPresentismDataByStudentIdAndYear(Long studentId, int year) {
		List<Course> courses = this.courseRepository.findByStudentsId(studentId)
				.stream()
				.filter(course -> course.getYear() == year)
				.collect(Collectors.toList());
		
		Map<Course, List<ClassStudentPresent>> presentismGroupedByCourse = courses.stream().collect(
				Collectors.toMap(
						course -> course, 
						course -> course.getClasses()
							.stream()
							.map(courseClass -> courseClass.getStudentPresents())
							.flatMap(Collection::stream)
							.filter(studentPresent -> studentPresent.getStudent().getId().equals(studentId))
							.collect(Collectors.toList())));
		return presentAnalysisCalculator.getPresentismByStudentGroupedByCourse(presentismGroupedByCourse);
	}
	
}
