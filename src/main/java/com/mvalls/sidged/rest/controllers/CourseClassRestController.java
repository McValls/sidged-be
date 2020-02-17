package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mvalls.sidged.annotations.JwtTeacher;
import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.mappers.ClassStudentPresentMapper;
import com.mvalls.sidged.mappers.CourseClassMapper;
import com.mvalls.sidged.mappers.CourseClassModelMapper;
import com.mvalls.sidged.model.ClassState;
import com.mvalls.sidged.model.ClassStudentPresent;
import com.mvalls.sidged.model.CourseClass;
import com.mvalls.sidged.rest.dtos.ClassStudentDTO;
import com.mvalls.sidged.rest.dtos.CourseClassCommentDTO;
import com.mvalls.sidged.rest.dtos.CourseClassDTO;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.services.CourseClassService;

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
