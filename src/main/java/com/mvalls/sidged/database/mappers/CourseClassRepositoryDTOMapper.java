package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.CourseClassDTO;

public class CourseClassRepositoryDTOMapper implements RepositoryDTOMapper<CourseClass, CourseClassDTO>{

	private final CourseRepositoryDTOMapper courseDTOMapper = new CourseRepositoryDTOMapper();
	
	@Override
	public CourseClass dtoToModel(CourseClassDTO dto) {
		return CourseClass.builder()
				.id(dto.getId())
				.classNumber(dto.getClassNumber())
				.date(dto.getDate())
				.classState(dto.getClassState())
				.course(courseDTOMapper.dtoToModel(dto.getCourse()))
				.build();
	}
	
	@Override
	public CourseClassDTO modelToDto(CourseClass model) {
		return CourseClassDTO.builder()
				.id(model.getId())
				.classNumber(model.getClassNumber())
				.date(model.getDate())
				.classState(model.getClassState())
				.course(courseDTOMapper.modelToDto(model.getCourse()))
				.build();
	}
	
}
