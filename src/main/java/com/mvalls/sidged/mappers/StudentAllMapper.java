package com.mvalls.sidged.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;

@Component
public class StudentAllMapper extends GenericMapper<Student, StudentAllDTO>{
	
	@Autowired
	private ContactDataMapper contactDataMapper;
	
	@Override
	public StudentAllDTO map(Student student) {
		return StudentAllDTO.builder()
				.id(student.getId())
				.names(student.getNames())
				.lastname(student.getLastname())
				.identificationNumber(student.getIdentificationNumber())
				.legacyNumber(student.getLegacyNumber())
				.contactData(contactDataMapper.map(student.getContactData()))
				.build();
	}
	
}
