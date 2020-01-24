package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;

@Component
public class TeacherAllMapper extends GenericMapper<Teacher, TeacherAllDTO>{
	
	@Autowired
	private ContactDataMapper contactDataMapper;
	
	@Override
	public TeacherAllDTO map(Teacher teacher) {
		return TeacherAllDTO.builder()
			.id(teacher.getId())
			.names(teacher.getNames())
			.lastname(teacher.getLastname())
			.legacyNumber(teacher.getLegacyNumber())
			.contactData(contactDataMapper.map(teacher.getContactData()))
			.build();
	}
	
}
