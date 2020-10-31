package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.database.mappers.UserRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.UserJpaRepository;

public class UserDatabaseRepository extends CommonDatabaseRepository<User, com.mvalls.sidged.database.dtos.login.User, UserJpaRepository> 
	implements UserRepository {

	public UserDatabaseRepository(UserJpaRepository jpaRepository, UserRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

	@Override
	public User getUserByUsername(String username) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.getUserByUsername(username));
	}
	

}
