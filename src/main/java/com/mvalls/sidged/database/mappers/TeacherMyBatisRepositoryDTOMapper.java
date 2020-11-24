package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.TeacherMyBatisDTO;

public class TeacherMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<Teacher, TeacherMyBatisDTO>{

	private final ContactDataMyBatisRepositoryDTOMapper contactDataDTOMapper = new ContactDataMyBatisRepositoryDTOMapper();
	
	@Override
	public Teacher dtoToModel(TeacherMyBatisDTO dto) {
		return Teacher.builder()
				.id(dto.getId())
				.legacyNumber(dto.getLegacyNumber())
				.names(dto.getNames())
				.lastname(dto.getLastname())
				.contactData(contactDataDTOMapper.dtoToModel(dto.getContactData()))
				.build();
	}

	@Override
	public TeacherMyBatisDTO modelToDto(Teacher model) {
		return TeacherMyBatisDTO.builder()
				.id(model.getId())
				.legacyNumber(model.getLegacyNumber())
				.names(model.getNames())
				.lastname(model.getLastname())
				.contactData(contactDataDTOMapper.modelToDto(model.getContactData()))
				.build();
	}

}
