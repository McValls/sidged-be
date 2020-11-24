package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.database.mappers.UserMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.UserRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.UserMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.UserMapper;

public class UserDatabaseRepository implements UserRepository {

	private final UserMapper userMapper;
	private final UserMyBatisRepositoryDTOMapper userMyBatisDTOMapper = new UserMyBatisRepositoryDTOMapper();

	public UserDatabaseRepository(UserMapper userMapper, UserRepositoryDTOMapper dtoMapper) {
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
		UserMyBatisDTO dto = this.userMyBatisDTOMapper.modelToDto(user);
		this.userMapper.insert(dto);
		user.setId(dto.getId());
		return user;
	}

	@Override
	public User update(User user) {
		UserMyBatisDTO dto = this.userMyBatisDTOMapper.modelToDto(user);
		this.userMapper.update(dto);
		return user;
	}

	//TODO: LISKOV!!
	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
