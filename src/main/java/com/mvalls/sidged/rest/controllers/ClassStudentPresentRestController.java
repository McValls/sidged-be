package com.mvalls.sidged.rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.services.ClassStudentPresentService;
import com.mvalls.sidged.rest.annotations.JwtTeacher;
import com.mvalls.sidged.rest.dtos.StudentPresentDTO;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;

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
@RestController
@RequestMapping("/student-present")
public class ClassStudentPresentRestController {

	private final ClassStudentPresentService classStudentPresentService;

	@Autowired
	public ClassStudentPresentRestController(ClassStudentPresentService classStudentPresentService) {
		super();
		this.classStudentPresentService = classStudentPresentService;
	}

	@JwtTeacher
	@PutMapping("/course/{courseCode}/class/{classNumber}/student/{studentId}")
	public void update(HttpServletRequest request,
			UserTeacher userTeacher,
			@PathVariable("courseCode") String courseCode,
			@PathVariable("classNumber") Integer classNumber,
			@PathVariable("studentId") Long studentId, @RequestBody StudentPresentDTO present)
			throws UnauthorizedUserException {
		classStudentPresentService.updatePresent(userTeacher.getTeacher(), courseCode, classNumber, studentId, present.getPresent());
	}

}
