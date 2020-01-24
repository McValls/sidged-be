package com.mvalls.sidged.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.CourseClass;

public interface CourseClassRepository extends JpaRepository<CourseClass, Long>{
	
	CourseClass findByCourseIdAndId(Long courseId, Long id);
	Collection<CourseClass> findByCourseYearOrderByCourseIdAscClassNumberDesc(Integer year);
	
}
