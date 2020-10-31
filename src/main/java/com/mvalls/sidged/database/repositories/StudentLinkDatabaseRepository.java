package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.StudentLink;
import com.mvalls.sidged.core.repositories.StudentLinkRepository;
import com.mvalls.sidged.database.mappers.StudentLinkRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.StudentLinkJpaRepository;

public class StudentLinkDatabaseRepository extends CommonDatabaseRepository<StudentLink, com.mvalls.sidged.database.dtos.StudentLinkDTO, StudentLinkJpaRepository>
	implements StudentLinkRepository {
	
	public StudentLinkDatabaseRepository(StudentLinkJpaRepository jpaRepository, StudentLinkRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

}
