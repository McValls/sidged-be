package com.mvalls.sidged.rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.annotations.JwtTeacher;
import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.rest.dtos.StudentPresentDTO;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.services.ClassStudentPresentService;

@RestController
@RequestMapping("/student-present")
public class ClassStudentPresentRestController {

	@Autowired private ClassStudentPresentService classStudentPresentService;
	
	@JwtTeacher
	@PutMapping("/class/{classId}/student/{studentId}")
	public void update(HttpServletRequest request, 
			UserTeacher userTeacher,
			@PathVariable("classId") Long classId,
			@PathVariable("studentId") Long studentId,
			@RequestBody StudentPresentDTO present) throws UnauthorizedUserException {
		classStudentPresentService.updatePresent(userTeacher.getTeacher(), classId, studentId, present.getPresent());
	}
	
}
