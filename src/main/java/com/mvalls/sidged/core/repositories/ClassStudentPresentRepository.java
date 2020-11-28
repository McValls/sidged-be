package com.mvalls.sidged.core.repositories;

import java.util.List;
import java.util.Optional;

import com.mvalls.sidged.core.model.ClassStudentPresent;

public interface ClassStudentPresentRepository {

	List<ClassStudentPresent> findByCourseAndClassNumber(String courseCode, Integer classNumber);
	Optional<ClassStudentPresent> findByCourseCodeAndClassNumberAndStudentId(
			String courseCode, Integer classNumber, Long studentId);
	ClassStudentPresent create(ClassStudentPresent classStudentPresent);
	ClassStudentPresent update(ClassStudentPresent classStudentPresent);

}
