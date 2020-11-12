package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.services.CourseClassService;
import com.mvalls.sidged.mappers.ClassStudentPresentMapper;
import com.mvalls.sidged.mappers.CourseClassCreateResponseMapper;
import com.mvalls.sidged.rest.annotations.JwtTeacher;
import com.mvalls.sidged.rest.dtos.ClassStudentDTO;
import com.mvalls.sidged.rest.dtos.CourseClassCommentDTO;
import com.mvalls.sidged.rest.dtos.courseClass.CourseClassCreateRequestDTO;
import com.mvalls.sidged.rest.dtos.courseClass.CourseClassCreateResponseDTO;
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
@RestController
@RequestMapping("/class")
public class CourseClassRestController {
	
	private final CourseClassService courseClassService;
	private final ClassStudentPresentMapper classStudentPresentMapper;
	private final CourseClassCreateResponseMapper courseClassCreateResponseMapper;
	
	@Autowired
	public CourseClassRestController(CourseClassService courseClassService) {
		super();
		this.courseClassService = courseClassService;
		this.classStudentPresentMapper = new ClassStudentPresentMapper();
		this.courseClassCreateResponseMapper = new CourseClassCreateResponseMapper();
	}

	@JwtTeacher
	@PostMapping
	public CourseClassCreateResponseDTO createClass(HttpServletRequest request, 
			UserTeacher userTeacher,
			@RequestBody CourseClassCreateRequestDTO createDTO) throws UnauthorizedUserException {
		CourseClass courseClass = courseClassService.create(userTeacher.getTeacher(), createDTO.getCourseCode(), createDTO.getDate());
		return courseClassCreateResponseMapper.map(courseClass);
	}
	
	@JwtTeacher
	@GetMapping("/{classNumber}/course/{courseCode}")
	public CourseClassCreateResponseDTO getClassByNumberAndCourseCode(HttpServletRequest request, 
			UserTeacher userTeacher, 
			@PathVariable("classNumber") Integer classNumber,
			@PathVariable("courseCode") String courseCode) throws UnauthorizedUserException {
		CourseClass courseClass = courseClassService.findByTeacherAndCourseCodeAndClassNumber(userTeacher.getTeacher(), courseCode, classNumber);
		return courseClassCreateResponseMapper.map(courseClass);
	}
		
	@GetMapping("/course/{courseCode}")
	public Collection<CourseClassCreateResponseDTO> getClassesByCourse(@PathVariable("courseCode") String courseCode){
		Collection<CourseClass> classes = courseClassService.findClassesByCourseCode(courseCode);
		Collection<CourseClassCreateResponseDTO> classesDTO = classes.stream()
				.map(course -> courseClassCreateResponseMapper.map(course))
				.sorted((CourseClassCreateResponseDTO c1, CourseClassCreateResponseDTO c2) -> c1.getClassNumber() - c2.getClassNumber())
				.collect(Collectors.toList());
		
		return classesDTO;
	}
	
	@GetMapping("/{classNumber}/course/{courseCode}/students")
	public Collection<ClassStudentDTO> getStudentsByClass(@PathVariable("classNumber") Integer classNumber,
			@PathVariable("courseCode") String courseCode){
		Collection<ClassStudentPresent> classStudents = courseClassService.getClassStudents(courseCode, classNumber);
		Collection<ClassStudentDTO> studentsDTO = classStudents.stream()
				.map(student -> classStudentPresentMapper.map(student))
				.collect(Collectors.toList());
		
		return studentsDTO;	
	}
	
	@Deprecated
	@PutMapping("/{classNumber}")
	public void updateComments(@PathVariable("classId") Long classId, 
			@RequestBody CourseClassCommentDTO courseClassCommentDTO) {
		courseClassService.updateComments(classId, courseClassCommentDTO.getComments());
	}
	
	@JwtTeacher
	@PutMapping("/finish/{classNumber}/course/{courseCode}")
	public void finishClass(HttpServletRequest request, 
			UserTeacher userTeacher,			
			@PathVariable("classNumber") Integer classNumber, 
			@PathVariable("courseCode") String courseCode,
			@RequestBody ClassStudentDTO studentDTO) throws UnauthorizedUserException {
		courseClassService.updateClassState(userTeacher.getTeacher(), courseCode, classNumber, ClassState.FINALIZADA);
	}

	@JwtTeacher
	@PutMapping("/reopen/{classNumber}/course/{courseCode}")
	public void reopenClass(HttpServletRequest request, 
			UserTeacher userTeacher,
			@PathVariable("classNumber") Integer classNumber, 
			@PathVariable("courseCode") String courseCode,
			@RequestBody ClassStudentDTO studentDTO) throws UnauthorizedUserException {
		courseClassService.updateClassState(userTeacher.getTeacher(), courseCode, classNumber, ClassState.PENDIENTE);
	}

}
