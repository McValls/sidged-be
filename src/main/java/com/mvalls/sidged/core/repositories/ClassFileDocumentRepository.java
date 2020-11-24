package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.ClassFileDocument;

public interface ClassFileDocumentRepository extends GenericRepository<ClassFileDocument, Long>{

	List<ClassFileDocument> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber);
	List<ClassFileDocument> findByCourseCode(String courseCode);

}
