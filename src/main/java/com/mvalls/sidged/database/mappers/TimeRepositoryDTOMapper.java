package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.Time;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.TimeDTO;

public class TimeRepositoryDTOMapper implements RepositoryDTOMapper<Time, TimeDTO> {

	@Override
	public Time dtoToModel(TimeDTO dto) {
		return Time.builder()
				.id(dto.getId())
				.since(dto.getSince())
				.until(dto.getUntil())
				.build();
	}

	@Override
	public TimeDTO modelToDto(Time model) {
		return TimeDTO.builder()
				.id(model.getId())
				.since(model.getSince())
				.until(model.getUntil())
				.build();
	}

}
