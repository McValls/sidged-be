package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.rest.dtos.CourseListDTO;
import com.mvalls.sidged.rest.dtos.PeriodDTO;

@Component
public class CourseListMapper extends GenericMapper<Course, CourseListDTO>{
	
	@Override
	public CourseListDTO map(Course course) {
		return CourseListDTO.builder()
				.career(course.getCareer().getName())
				.id(course.getId())
				.name(course.getName())
				.period(PeriodDTO
						.builder()
						.periodType(course.getPeriod().getPeriodType())
						.number(course.getPeriod().getNumber())
						.build())
				.shift(course.getShift())
				.timeSince(course.getTimeStart().getSince().toString())
				.timeUntil(course.getTimeEnd().getUntil().toString())
				.year(course.getYear())
				.build();
	}

}
