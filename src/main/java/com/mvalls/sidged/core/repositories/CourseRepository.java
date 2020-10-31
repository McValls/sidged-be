package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Course;

public interface CourseRepository extends GenericRepository<Course, Long> {

	List<Course> findByTeachersId(Long teacherId);

	List<Course> findByStudentsId(Long studentId);

}
