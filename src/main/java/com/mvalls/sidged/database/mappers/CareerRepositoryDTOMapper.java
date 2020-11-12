package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.CareerDTO;

public class CareerRepositoryDTOMapper implements RepositoryDTOMapper<Career, CareerDTO> {

	@Override
	public Career dtoToModel(CareerDTO dto) {
		return Career.builder()
				.id(dto.getId())
				.code(dto.getCode())
				.name(dto.getName())
				.build();
	}

	@Override
	public CareerDTO modelToDto(Career model) {
		CareerDTO dto = new CareerDTO();
		dto.setId(model.getId());
		dto.setCode(model.getCode());
		dto.setName(model.getName());
		
		return dto;
	}

}
