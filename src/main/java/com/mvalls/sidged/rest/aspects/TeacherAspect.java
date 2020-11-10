package com.mvalls.sidged.rest.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.model.users.UserType;
import com.mvalls.sidged.core.services.UserTeacherService;
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
public class TeacherAspect {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private UserTeacherService userTeacherService;
	
	@Pointcut("@annotation(com.mvalls.sidged.rest.annotations.JwtTeacher) && args(request, userTeacher,..)")
	private void getCoursesByTeacherExecution(HttpServletRequest request, UserTeacher userTeacher) {
		
	}
	
	@Before("getCoursesByTeacherExecution(request, userTeacher)")
	public void setTeacherInfo(HttpServletRequest request, UserTeacher userTeacher) throws UnauthorizedUserException {
		String header = request.getHeader("Authorization");
		String authToken = header.substring(7);
		
		String username = jwtTokenUtils.getUserName(authToken, UserType.TEACHER);
		UserTeacher dbUserTeacher = userTeacherService.findByUsername(username);
		
		userTeacher.setTeacher(dbUserTeacher.getTeacher());
		userTeacher.setUser(dbUserTeacher.getUser());
	}
	
}