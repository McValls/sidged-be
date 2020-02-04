package com.mvalls.sidged.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.security.jwt.JwtTokenUtils;
import com.mvalls.sidged.services.UserTeacherService;

@Aspect
@Component
public class TeacherAspect {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private UserTeacherService userTeacherService;
	
	@Pointcut("@annotation(com.mvalls.sidged.annotations.JwtTeacher) && args(request, userTeacher,..)")
	private void getCoursesByTeacherExecution(HttpServletRequest request, UserTeacher userTeacher) {
		
	}
	
	@Before("getCoursesByTeacherExecution(request, userTeacher)")
	public void setTeacherInfo(HttpServletRequest request, UserTeacher userTeacher) {
		String header = request.getHeader("Authorization");
		String authToken = header.substring(7);
		
		String username = jwtTokenUtils.getUserName(authToken, UserType.TEACHER);
		UserTeacher dbUserTeacher = userTeacherService.findByUsername(username);
		
		userTeacher.setTeacher(dbUserTeacher.getTeacher());
		userTeacher.setUser(dbUserTeacher.getUser());
	}
	
}