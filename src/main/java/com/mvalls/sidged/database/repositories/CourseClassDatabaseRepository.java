package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.database.dtos.CourseClassDTO;
import com.mvalls.sidged.database.mappers.CourseClassRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.CourseClassJpaRepository;

public class CourseClassDatabaseRepository extends CommonDatabaseRepository<CourseClass, CourseClassDTO, CourseClassJpaRepository>
	implements CourseClassRepository {
	
	public CourseClassDatabaseRepository(CourseClassJpaRepository jpaRepository, CourseClassRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

}
