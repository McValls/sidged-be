package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.rest.dtos.CourseDTO;
import com.mvalls.sidged.valueObjects.CourseVO;

@Component
public class CourseDTOtoVOMapper extends GenericMapper<CourseDTO, CourseVO>{

	@Override
	public CourseVO map(CourseDTO dto) {
		CourseVO valueObject = CourseVO.builder()
				.name(dto.getName())
				.shift(dto.getShift())
				.year(dto.getYear())
				.periodNumber(dto.getPeriodNumber())
				.periodType(dto.getPeriodType())
				.timeSinceId(dto.getTimeSinceId())
				.timeUntilId(dto.getTimeUntilId())
				.careerId(dto.getCareerId())
				.chair(dto.getChair())
				.build();
		return valueObject;
	}
	
}
