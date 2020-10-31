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

import com.mvalls.sidged.annotations.JwtTeacher;
import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.services.CourseClassService;
import com.mvalls.sidged.mappers.ClassStudentPresentMapper;
import com.mvalls.sidged.mappers.CourseClassMapper;
import com.mvalls.sidged.mappers.CourseClassModelMapper;
import com.mvalls.sidged.rest.dtos.ClassStudentDTO;
import com.mvalls.sidged.rest.dtos.CourseClassCommentDTO;
import com.mvalls.sidged.rest.dtos.CourseClassDTO;
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
	
	@Autowired private ClassStudentPresentMapper classStudentPresentMapper;
	@Autowired private CourseClassModelMapper courseClassModelMapper;
	@Autowired private CourseClassMapper courseClassMapper;
	@Autowired private CourseClassService courseClassService;

	@JwtTeacher
	@PostMapping
	public CourseClassDTO createClass(HttpServletRequest request, 
			UserTeacher userTeacher,
			@RequestBody CourseClassDTO classDTO) throws UnauthorizedUserException {
		CourseClass courseClass = courseClassModelMapper.map(classDTO);
		courseClass = courseClassService.create(userTeacher.getTeacher(), courseClass);
		return courseClassMapper.map(courseClass);
	}
	
	@JwtTeacher
	@GetMapping("/{classId}")
	public CourseClassDTO getClassById(HttpServletRequest request, 
			UserTeacher userTeacher, 
			@PathVariable("classId") Long classId) throws UnauthorizedUserException {
		CourseClass courseClass = courseClassService.findByTeacherAndId(userTeacher.getTeacher(), classId);
		return courseClassMapper.map(courseClass);
	}
		
	@GetMapping("/course/{courseId}")
	public Collection<CourseClassDTO> getClassesByCourse(@PathVariable("courseId") Long courseId){
		Collection<CourseClass> classes = courseClassService.findClassesByCourseId(courseId);
		Collection<CourseClassDTO> classesDTO = classes.stream()
				.map(course -> courseClassMapper.map(course))
				.sorted((CourseClassDTO c1, CourseClassDTO c2) -> c1.getClassNumber() - c2.getClassNumber())
				.collect(Collectors.toList());
		
		return classesDTO;
	}
	
	@GetMapping("/{classId}/course/{courseId}/students")
	public Collection<ClassStudentDTO> getStudentsByClass(@PathVariable("classId") Long classId,
			@PathVariable("courseId") Long courseId){
		Collection<ClassStudentPresent> classStudents = courseClassService.getClassStudents(courseId, classId);
		Collection<ClassStudentDTO> studentsDTO = classStudents.stream()
				.map(student -> classStudentPresentMapper.map(student))
				.collect(Collectors.toList());
		
		return studentsDTO;	
	}
	
	@PutMapping("/{classId}")
	public void updateComments(@PathVariable("classId") Long classId, 
			@RequestBody CourseClassCommentDTO courseClassCommentDTO) {
		courseClassService.updateComments(classId, courseClassCommentDTO.getComments());
	}
	
	@JwtTeacher
	@PutMapping("/finish/{classId}/course/{courseId}")
	public void finishClass(HttpServletRequest request, 
			UserTeacher userTeacher,			
			@PathVariable("classId") Long classId, 
			@PathVariable("courseId") Long courseId,
			@RequestBody ClassStudentDTO studentDTO) throws UnauthorizedUserException {
		courseClassService.updateClassState(userTeacher.getTeacher(), courseId, classId, ClassState.FINALIZADA);
	}

	@JwtTeacher
	@PutMapping("/reopen/{classId}/course/{courseId}")
	public void reopenClass(HttpServletRequest request, 
			UserTeacher userTeacher,
			@PathVariable("classId") Long classId, 
			@PathVariable("courseId") Long courseId,
			@RequestBody ClassStudentDTO studentDTO) throws UnauthorizedUserException {
		courseClassService.updateClassState(userTeacher.getTeacher(), courseId, classId, ClassState.PENDIENTE);
	}

}
