package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.ClassFileDocument;

public interface ClassFileDocumentRepository {

	List<ClassFileDocument> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber);
	List<ClassFileDocument> findByCourseCode(String courseCode);
	ClassFileDocument findById(Long id);
	ClassFileDocument create(ClassFileDocument classFileDocument);

}
