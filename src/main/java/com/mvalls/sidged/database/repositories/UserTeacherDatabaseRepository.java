package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;
import com.mvalls.sidged.database.mappers.UserTeacherRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.UserTeacherJpaRepository;

public class UserTeacherDatabaseRepository extends CommonDatabaseRepository<UserTeacher, com.mvalls.sidged.database.dtos.login.UserTeacherDTO, UserTeacherJpaRepository>
	implements UserTeacherRepository {
	
	public UserTeacherDatabaseRepository(UserTeacherJpaRepository jpaRepository, UserTeacherRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
	@Override
	public UserTeacher findByTeacherId(Long id) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.findByTeacherId(id));
	}

	@Override
	public UserTeacher findByUserUsername(String username) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.findByUserUsername(username));
	}

}
