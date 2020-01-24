package com.mvalls.sidged.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	
	Collection<Course> findByTeachersId(Long id);
	Collection<Course> findByStudentsId(Long id);

}
