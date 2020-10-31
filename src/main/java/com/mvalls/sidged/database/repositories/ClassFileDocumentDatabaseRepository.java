package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.database.mappers.ClassFileDocumentRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.ClassFileDocumentJpaRepository;

public class ClassFileDocumentDatabaseRepository extends CommonDatabaseRepository<ClassFileDocument, com.mvalls.sidged.database.dtos.ClassFileDocumentDTO, ClassFileDocumentJpaRepository> implements ClassFileDocumentRepository {
	
	public ClassFileDocumentDatabaseRepository(ClassFileDocumentJpaRepository jpaRepository,
			ClassFileDocumentRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

	@Override
	public List<ClassFileDocument> findByCourseClassId(Long classId) {
		return this.jpaRepository.findByCourseClassId(classId)
			.stream()
			.map(dtoMapper::dtoToModel)
			.collect(Collectors.toList());
	}

	@Override
	public List<ClassFileDocument> findByCourseClassCourseId(Long courseId) {
		return this.jpaRepository.findByCourseClassCourseId(courseId)
				.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

}
