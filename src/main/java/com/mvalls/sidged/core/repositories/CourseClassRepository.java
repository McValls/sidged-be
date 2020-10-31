package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.CourseClass;

public interface CourseClassRepository extends GenericRepository<CourseClass, Long> {

	List<CourseClass> findByCourseYearAndClassState(int year, ClassState classState);

	CourseClass findByCourseIdAndId(Long courseId, Long classId);

}
