package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.ClassStudentPresent;

public interface ClassStudentPresentRepository extends GenericRepository<ClassStudentPresent, Long> {

	ClassStudentPresent findByCourseClassIdAndStudentId(Long courseClassId, Long studentId);

	List<ClassStudentPresent> findByStudentId(Long studentId);


}
