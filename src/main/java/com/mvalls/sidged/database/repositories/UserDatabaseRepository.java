package com.mvalls.sidged.database.repositories;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.database.dtos.UserDTO;
import com.mvalls.sidged.database.mybatis.mappers.UserMapper;
import com.mvalls.sidged.database.repositories.mappers.UserRepositoryDTOMapper;

public class UserDatabaseRepository implements UserRepository {

	private final UserMapper userMapper;
	private final UserRepositoryDTOMapper userMyBatisDTOMapper = new UserRepositoryDTOMapper();

	public UserDatabaseRepository(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@Override
	public Optional<User> findByUserName(String username) {
		return this.userMapper.findByUserName(username)
				.map(userMyBatisDTOMapper::dtoToModel);
				
	}

	@Override
	@Transactional
	public User create(User user) {
		UserDTO dto = this.userMyBatisDTOMapper.modelToDto(user);
		this.userMapper.insert(dto);
		user.setId(dto.getId());
		return user;
	}

	@Override
	public User update(User user) {
		UserDTO dto = this.userMyBatisDTOMapper.modelToDto(user);
		this.userMapper.update(dto);
		return user;
	}

}
