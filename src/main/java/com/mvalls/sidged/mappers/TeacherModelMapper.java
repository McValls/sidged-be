package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;

@Component
public class TeacherModelMapper extends GenericMapper<TeacherAllDTO, Teacher>{

	@Autowired
	private ContactDataModelMapper contactDataModelMapper;
	
	@Override
	public Teacher map(TeacherAllDTO dto) {
		return Teacher.builder()
			.id(dto.getId())
			.names(dto.getNames())
			.lastname(dto.getLastname())
			.contactData(contactDataModelMapper.map(dto.getContactData()))
			.build();
	}
	
}
