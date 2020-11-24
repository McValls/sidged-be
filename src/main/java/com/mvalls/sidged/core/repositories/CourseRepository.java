package com.mvalls.sidged.core.repositories;

import java.util.List;
import java.util.Optional;

import com.mvalls.sidged.core.model.Course;

public interface CourseRepository extends GenericRepository<Course, Long> {

	List<Course> findByTeacherId(Long teacherId);

	List<Course> findByStudentsId(Long studentId);
	
	Optional<Course> findByCourseClassId(Long courseClassId);

	List<Course> findByYear(Integer year);
	
	Optional<Course> findByCode(String code);

}
