package com.mvalls.sidged.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.ClassFileDocument;

public interface ClassFileDocumentRepository extends JpaRepository<ClassFileDocument, Long> {
	
	Collection<ClassFileDocument> findByCourseClassId(Long courseClassId);

	Collection<ClassFileDocument> findByCourseClassCourseId(Long courseId);

}
