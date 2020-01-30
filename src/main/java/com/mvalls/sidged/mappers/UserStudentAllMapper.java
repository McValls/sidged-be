package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;

@Component
public class UserStudentAllMapper extends GenericMapper<UserStudent, StudentAllDTO>{
	
	@Autowired
	private ContactDataMapper contactDataMapper;
	
	@Override
	public StudentAllDTO map(UserStudent userStudent) {
		Student student = userStudent.getStudent();
		return StudentAllDTO.builder()
				.id(student.getId())
				.username(userStudent.getUser().getUsername())
				.names(student.getNames())
				.lastname(student.getLastname())
				.identificationNumber(student.getIdentificationNumber())
				.legacyNumber(student.getLegacyNumber())
				.contactData(contactDataMapper.map(student.getContactData()))
				.build();
	}
	
}
