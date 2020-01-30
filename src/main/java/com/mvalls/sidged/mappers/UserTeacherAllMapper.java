package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;

@Component
public class UserTeacherAllMapper extends GenericMapper<UserTeacher, TeacherAllDTO>{
	
	@Autowired
	private ContactDataMapper contactDataMapper;
	
	@Override
	public TeacherAllDTO map(UserTeacher userTeacher) {
		Teacher teacher = userTeacher.getTeacher();
		return TeacherAllDTO.builder()
			.id(teacher.getId())
			.username(userTeacher.getUser().getUsername())
			.names(teacher.getNames())
			.lastname(teacher.getLastname())
			.legacyNumber(teacher.getLegacyNumber())
			.contactData(contactDataMapper.map(teacher.getContactData()))
			.build();
	}
	
}
