package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.PeriodDTO;

public class PeriodRepositoryDTOMapper implements RepositoryDTOMapper<Period, PeriodDTO>{

	@Override
	public Period dtoToModel(PeriodDTO dto) {
		return Period.builder()
				.id(dto.getId())
				.periodType(dto.getPeriodType())
				.number(dto.getNumber())
				.build();
	}

	@Override
	public PeriodDTO modelToDto(Period model) {
		return PeriodDTO.builder()
				.id(model.getId())
				.periodType(model.getPeriodType())
				.number(model.getNumber())
				.build();
	}

}
