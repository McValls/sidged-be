package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.StudentDTO;

public class StudentRepositoryDTOMapper implements RepositoryDTOMapper<Student, StudentDTO>{

	private final ContactDataRepositoryDTOMapper contactDataDTOMapper = new ContactDataRepositoryDTOMapper();
	
	@Override
	public Student dtoToModel(StudentDTO dto) {
		return Student.builder()
				.id(dto.getId())
				.legacyNumber(dto.getLegacyNumber())
				.names(dto.getNames())
				.lastname(dto.getLastname())
				.identificationNumber(dto.getIdentificationNumber())
				.contactData(contactDataDTOMapper.dtoToModel(dto.getContactData()))
				.build();
	}

	@Override
	public StudentDTO modelToDto(Student model) {
		return StudentDTO.builder()
				.id(model.getId())
				.legacyNumber(model.getLegacyNumber())
				.names(model.getNames())
				.lastname(model.getLastname())
				.identificationNumber(model.getIdentificationNumber())
				.contactData(contactDataDTOMapper.modelToDto(model.getContactData()))
				.build();
	}

}
