package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.TeacherDTO;

public class TeacherRepositoryDTOMapper implements RepositoryDTOMapper<Teacher, TeacherDTO>{

	private final ContactDataRepositoryDTOMapper contactDataDTOMapper = new ContactDataRepositoryDTOMapper();
	
	@Override
	public Teacher dtoToModel(TeacherDTO dto) {
		return Teacher.builder()
				.id(dto.getId())
				.legacyNumber(dto.getLegacyNumber())
				.names(dto.getNames())
				.lastname(dto.getLastname())
				.contactData(contactDataDTOMapper.dtoToModel(dto.getContactData()))
				.build();
	}

	@Override
	public TeacherDTO modelToDto(Teacher model) {
		return TeacherDTO.builder()
				.id(model.getId())
				.legacyNumber(model.getLegacyNumber())
				.names(model.getNames())
				.lastname(model.getLastname())
				.contactData(contactDataDTOMapper.modelToDto(model.getContactData()))
				.build();
	}

}
