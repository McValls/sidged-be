package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.database.mappers.StudentRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.StudentJpaRepository;

public class StudentDatabaseRepository extends CommonDatabaseRepository<Student, com.mvalls.sidged.database.dtos.StudentDTO, StudentJpaRepository>
	implements StudentRepository {

	public StudentDatabaseRepository(StudentJpaRepository jpaRepository, StudentRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
}
