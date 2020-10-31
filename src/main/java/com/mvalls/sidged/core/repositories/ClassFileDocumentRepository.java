package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.ClassFileDocument;

public interface ClassFileDocumentRepository extends GenericRepository<ClassFileDocument, Long>{

	List<ClassFileDocument> findByCourseClassId(Long classId);
	List<ClassFileDocument> findByCourseClassCourseId(Long courseId);

}
