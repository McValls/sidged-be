package com.mvalls.sidged.mappers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentDTO;

@Component
public class StudentModelMapper extends GenericMapper<StudentDTO, Student>{

	@Autowired
	private ContactDataModelMapper contactDataModelMapper;
	
	@Override
	public Student map(StudentDTO dto) {
		Student.StudentBuilder builder = Student.builder();
		builder.legacyNumber(dto.getLegacyNumber())
			.id(dto.getId())
			.names(dto.getNames())
			.lastname(dto.getLastname())
			.identificationNumber(dto.getIdentificationNumber())
			.contactData(contactDataModelMapper.map(dto.getContactData()));
		
		if(dto.getBase64EncodedPic() != null) {
			builder.perfilPic(Base64.getDecoder().decode(dto.getBase64EncodedPic()));
		}
		
		return builder.build();
	}
	
}
