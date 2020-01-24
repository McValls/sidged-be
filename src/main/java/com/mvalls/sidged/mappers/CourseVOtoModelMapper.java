package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.valueObjects.CourseVO;

@Component
public class CourseVOtoModelMapper extends GenericMapper<CourseVO, Course>{

	@Override
	public Course map(CourseVO vo) {
		Course course = Course.builder()
				.name(vo.getName())
				.shift(vo.getShift())
				.year(vo.getYear())
				.chair(vo.getChair())
				.build();
		return course;
	}

	
}
