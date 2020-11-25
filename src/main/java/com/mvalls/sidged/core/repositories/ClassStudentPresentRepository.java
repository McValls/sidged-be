package com.mvalls.sidged.core.repositories;

import java.util.List;
import java.util.Optional;

import com.mvalls.sidged.core.model.ClassStudentPresent;

public interface ClassStudentPresentRepository extends GenericRepository<ClassStudentPresent, Long> {

	List<ClassStudentPresent> findByCourseAndClassNumber(String courseCode, Integer classNumber);
	Optional<ClassStudentPresent> findByCourseCodeAndClassNumberAndStudentId(
			String courseCode, Integer classNumber, Long studentId);

}
