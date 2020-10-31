package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.database.mappers.TeacherRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.TeacherJpaRepository;

public class TeacherDatabaseRepository extends CommonDatabaseRepository<Teacher, com.mvalls.sidged.database.dtos.TeacherDTO, TeacherJpaRepository>
	implements TeacherRepository {

	public TeacherDatabaseRepository(TeacherJpaRepository jpaRepository, TeacherRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
}
