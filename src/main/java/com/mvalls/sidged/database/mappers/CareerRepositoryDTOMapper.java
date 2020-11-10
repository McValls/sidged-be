package com.mvalls.sidged.database.mappers;

import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.CareerDTO;

public class CareerRepositoryDTOMapper implements RepositoryDTOMapper<Career, CareerDTO> {

	private final CourseRepositoryDTOMapper courseMapper = new CourseRepositoryDTOMapper();
	
	@Override
	public Career dtoToModel(CareerDTO dto) {
		return Career.builder()
				.id(dto.getId())
				.name(dto.getName())
				.courses(dto.getCourse()
						.stream()
						.map(courseMapper::dtoToModel)
						.collect(Collectors.toList())
				)
				.build();
	}

	@Override
	public CareerDTO modelToDto(Career model) {
		CareerDTO dto = new CareerDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setCourse(model.getCourses()
				.stream()
				.map(courseMapper::modelToDto)
				.collect(Collectors.toList()));
		
		return dto;
	}

}
