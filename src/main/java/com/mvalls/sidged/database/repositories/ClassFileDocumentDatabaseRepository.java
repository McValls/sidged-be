package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.database.dtos.ClassFileDocumentDTO;
import com.mvalls.sidged.database.mappers.ClassFileDocumentRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.ClassFileDocumentJpaRepository;

public class ClassFileDocumentDatabaseRepository extends CommonDatabaseRepository<ClassFileDocument, ClassFileDocumentDTO, ClassFileDocumentJpaRepository> 
	implements ClassFileDocumentRepository {
	
	public ClassFileDocumentDatabaseRepository(ClassFileDocumentJpaRepository jpaRepository,
			ClassFileDocumentRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

}
