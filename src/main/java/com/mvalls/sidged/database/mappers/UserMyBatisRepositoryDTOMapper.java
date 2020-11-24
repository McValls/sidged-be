package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.UserMyBatisDTO;

public class UserMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<User, UserMyBatisDTO>{

	@Override
	public User dtoToModel(UserMyBatisDTO dto) {
		return User.builder()
				.id(dto.getId())
				.username(dto.getUsername())
				.password(dto.getPassword())
				.email(dto.getEmail())
				.userType(dto.getUserType())
				.userStatus(dto.getUserStatus())
				.lastTimeLogged(dto.getLastTimeLogged())
				.lastTimeModified(dto.getLastTimeModified())
				.build();
	}

	@Override
	public UserMyBatisDTO modelToDto(User model) {
		return UserMyBatisDTO.builder()
				.id(model.getId())
				.username(model.getUsername())
				.password(model.getPassword())
				.email(model.getEmail())
				.userType(model.getUserType())
				.userStatus(model.getUserStatus())
				.lastTimeLogged(model.getLastTimeLogged())
				.lastTimeModified(model.getLastTimeModified())
				.build();
	}

}
