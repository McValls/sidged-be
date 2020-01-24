package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.model.CourseClass;
import com.mvalls.sidged.rest.dtos.CourseClassDTO;

@Component
public class CourseClassModelMapper extends GenericMapper<CourseClassDTO, CourseClass>{

	@Override
	public CourseClass map(CourseClassDTO dto) {
		CourseClass courseClass = CourseClass.builder()
				.date(dto.getDate())
				.course(buildCourse(dto))
				.build();
		return courseClass;
	}
	
	private Course buildCourse(CourseClassDTO dto) {
		return Course.builder()
				.id(dto.getCourseId())
				.build();
	}

}
