package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.rest.dtos.LoginResponseDTO;
import com.mvalls.sidged.services.UserStudentService;
import com.mvalls.sidged.services.UserTeacherService;

@Component
public class LoginResponseMapper extends GenericMapper<User, LoginResponseDTO>{

	private final UserStudentService userStudentService;
	private final UserTeacherService userTeacherService;
	
	@Autowired
	public LoginResponseMapper(UserStudentService userStudentService, 
			UserTeacherService userTeacherService) {
		this.userStudentService = userStudentService;
		this.userTeacherService = userTeacherService;
	}
	
	@Override
	public LoginResponseDTO map(User loggedUser) {
		return LoginResponseDTO.builder()
			.username(loggedUser.getUsername())
			.userType(loggedUser.getUserType())
			.fullName(getFullName(loggedUser))
			.userStatus(loggedUser.getUserStatus())
			.build();
	}
	
	private String getFullName(User user) {
		if(user.getUserType() == UserType.STUDENT) {
			Student student = userStudentService.findByUsername(user.getUsername()).getStudent();
			return student.getNames() + " " + student.getLastname();
		} else if(user.getUserType() == UserType.TEACHER){
			Teacher teacher = userTeacherService.findByUsername(user.getUsername()).getTeacher();
			return teacher.getNames() + " " + teacher.getLastname();
		} else {
			return "";
		}
	}
	
}
