package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserType;
import com.mvalls.sidged.core.services.UserStudentService;
import com.mvalls.sidged.core.services.UserTeacherService;
import com.mvalls.sidged.rest.dtos.LoginResponseDTO;

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
