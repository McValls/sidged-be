package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.UserStudentRepository;
import com.mvalls.sidged.database.mappers.UserStudentRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.UserStudentJpaRepository;

public class UserStudentDatabaseRepository extends CommonDatabaseRepository<UserStudent, com.mvalls.sidged.database.dtos.login.UserStudentDTO, UserStudentJpaRepository>
	implements UserStudentRepository {
	
	public UserStudentDatabaseRepository(UserStudentJpaRepository jpaRepository, UserStudentRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
	@Override
	public UserStudent findByUserUsername(String username) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.findByUserUsername(username));
	}

	@Override
	public UserStudent findByStudentId(Long id) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.findByStudentId(id));
	}

}
