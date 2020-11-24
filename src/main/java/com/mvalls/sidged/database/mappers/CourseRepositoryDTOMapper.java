package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.dtos.PeriodDTO;
import com.mvalls.sidged.database.dtos.TimeDTO;
import com.mvalls.sidged.database.mybatis.dtos.CourseDTO;

public class CourseRepositoryDTOMapper implements RepositoryDTOMapper<Course, CourseDTO> {

	private final CareerRepositoryDTOMapper careerDTOMapper = new CareerRepositoryDTOMapper();
	private final PeriodRepositoryDTOMapper periodDTOMapper = new PeriodRepositoryDTOMapper();
	private final TimeRepositoryDTOMapper timeDTOMapper = new TimeRepositoryDTOMapper();
	
	@Override
	public Course dtoToModel(CourseDTO dto) {
		return Course.builder()
				.id(dto.getId())
				.code(dto.getCode())
				.name(dto.getName())
				.shift(dto.getShift())
				.year(dto.getYear())
				.career(careerDTOMapper.dtoToModel(dto.getCareer()))
				.period(periodDTOMapper.dtoToModel(dto.getPeriod()))
				.timeStart(timeDTOMapper.dtoToModel(dto.getTimeStart()))
				.timeEnd(timeDTOMapper.dtoToModel(dto.getTimeEnd()))
				.build();
	}

	@Override
	public CourseDTO modelToDto(Course model) {
		return CourseDTO.builder()
				.id(model.getId())
				.code(model.getCode())
				.name(model.getName())
				.shift(model.getShift())
				.year(model.getYear())
				.career(CareerDTO.builder()
						.id(model.getCareer().getId())
						.build())
				.period(PeriodDTO.builder()
						.id(model.getPeriod().getId())
						.build())
				.timeStart(TimeDTO.builder()
						.id(model.getTimeStart().getId())
						.build())
				.timeEnd(TimeDTO.builder()
						.id(model.getTimeEnd().getId())
						.build())
				.career(CareerDTO.builder()
						.id(model.getCareer().getId())
						.build())
				.build();
	}
	
}
