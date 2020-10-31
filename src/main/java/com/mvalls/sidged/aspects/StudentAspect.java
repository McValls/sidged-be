package com.mvalls.sidged.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.model.users.UserType;
import com.mvalls.sidged.core.services.UserStudentService;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.rest.security.jwt.JwtTokenUtils;

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
@Aspect
@Component
public class StudentAspect {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private UserStudentService userStudentService;
	
	@Pointcut("@annotation(com.mvalls.sidged.annotations.JwtStudent) && args(.., request, userStudent)")
	private void getCoursesByStudentsExecution(HttpServletRequest request, UserStudent userStudent) {
		
	}
	
	@Before("getCoursesByStudentsExecution(request, userStudent)")
	public void setStudentInfo(HttpServletRequest request, UserStudent userStudent) throws UnauthorizedUserException {
		String header = request.getHeader("Authorization");
		String authToken = header.substring(7);
		
		String username = jwtTokenUtils.getUserName(authToken, UserType.STUDENT);
		UserStudent dbUserStudent = userStudentService.findByUsername(username);
		
		userStudent.setStudent(dbUserStudent.getStudent());
		userStudent.setUser(dbUserStudent.getUser());
	}
	
}