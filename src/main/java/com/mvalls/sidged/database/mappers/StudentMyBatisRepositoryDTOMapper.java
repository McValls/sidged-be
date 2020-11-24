package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.StudentMyBatisDTO;

public class StudentMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<Student, StudentMyBatisDTO>{

	private final ContactDataMyBatisRepositoryDTOMapper contactDataDTOMapper = new ContactDataMyBatisRepositoryDTOMapper();
	
	@Override
	public Student dtoToModel(StudentMyBatisDTO dto) {
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
	public StudentMyBatisDTO modelToDto(Student model) {
		return StudentMyBatisDTO.builder()
				.id(model.getId())
				.legacyNumber(model.getLegacyNumber())
				.names(model.getNames())
				.lastname(model.getLastname())
				.identificationNumber(model.getIdentificationNumber())
				.contactData(contactDataDTOMapper.modelToDto(model.getContactData()))
				.build();
	}

}
