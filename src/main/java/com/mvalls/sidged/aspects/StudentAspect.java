package com.mvalls.sidged.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.security.jwt.JwtTokenUtils;
import com.mvalls.sidged.services.UserStudentService;

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