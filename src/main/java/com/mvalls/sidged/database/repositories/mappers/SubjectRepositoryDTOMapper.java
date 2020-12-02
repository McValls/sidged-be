package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.SubjectDTO;

public class SubjectRepositoryDTOMapper implements RepositoryDTOMapper<Subject, SubjectDTO>{

	private final CareerRepositoryDTOMapper careerDTOMapper = new CareerRepositoryDTOMapper();
	
	@Override
	public Subject dtoToModel(SubjectDTO dto) {
		return new Subject(dto.getId(), dto.getName(), dto.getCode(),
				careerDTOMapper.dtoToModel(dto.getCareer()));
	}

	@Override
	public SubjectDTO modelToDto(Subject model) {
		return SubjectDTO.builder()
				.id(model.getId())
				.name(model.getName())
				.code(model.getCode())
				.career(careerDTOMapper.modelToDto(model.getCareer()))
				.build();
	}

}
