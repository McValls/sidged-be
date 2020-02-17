package com.mvalls.sidged.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.security.jwt.JwtTokenUtils;

@Aspect
@Component
public class BackOfficeAspect {
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Pointcut("@annotation(com.mvalls.sidged.annotations.JwtBackOffice) && args(request, ..)")
	private void getBackOfficeProtectedExecution(HttpServletRequest request) {
		
	}
	
	@Before("getBackOfficeProtectedExecution(request)")
	public void setStudentInfo(HttpServletRequest request) throws UnauthorizedUserException {
		String header = request.getHeader("Authorization");
		String authToken = header.substring(7);
		
		jwtTokenUtils.getUserName(authToken, UserType.BACKOFFICE);
	}

}
