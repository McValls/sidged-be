package com.mvalls.sidged.core.repositories;

import java.util.List;
import java.util.Optional;

import com.mvalls.sidged.core.model.CourseClass;

public interface CourseClassRepository {
	
	List<CourseClass> findByCourseCode(String courseCode);
	Optional<CourseClass> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber);
	List<CourseClass> findAllByStudentId(Long studentId);
	CourseClass create(CourseClass courseClass);
	CourseClass update(CourseClass courseClass);
}
