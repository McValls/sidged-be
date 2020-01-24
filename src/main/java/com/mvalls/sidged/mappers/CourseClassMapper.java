package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.CourseClass;
import com.mvalls.sidged.rest.dtos.CourseClassDTO;

@Component
public class CourseClassMapper extends GenericMapper<CourseClass, CourseClassDTO>{

	@Override
	public CourseClassDTO map(CourseClass model) {
		return CourseClassDTO.builder()
				.id(model.getId())
				.courseId(model.getCourse().getId())
				.date(model.getDate())
				.classNumber(model.getClassNumber())
				.classState(model.getClassState())
				.build();
	}
	
}
