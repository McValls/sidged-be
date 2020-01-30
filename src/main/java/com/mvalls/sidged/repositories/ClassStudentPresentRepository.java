package com.mvalls.sidged.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.ClassStudentPresent;

public interface ClassStudentPresentRepository extends JpaRepository<ClassStudentPresent, Long>{

	ClassStudentPresent findByCourseClassIdAndStudentId(Long classId, Long studentId);
	
	Collection<ClassStudentPresent> findByStudentId(Long studentId);
	
}
