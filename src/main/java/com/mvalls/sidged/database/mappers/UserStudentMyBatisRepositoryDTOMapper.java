package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.UserStudentMyBatisDTO;

public class UserStudentMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<UserStudent, UserStudentMyBatisDTO>{

	private final UserMyBatisRepositoryDTOMapper userDTOMapper = new UserMyBatisRepositoryDTOMapper();
	private final StudentMyBatisRepositoryDTOMapper studentDTOMapper = new StudentMyBatisRepositoryDTOMapper();
	
	@Override
	public UserStudent dtoToModel(UserStudentMyBatisDTO dto) {
		return UserStudent.builder()
				.id(dto.getId())
				.user(userDTOMapper.dtoToModel(dto.getUser()))
				.student(studentDTOMapper.dtoToModel(dto.getStudent()))
				.build();
	}

	@Override
	public UserStudentMyBatisDTO modelToDto(UserStudent model) {
		return UserStudentMyBatisDTO.builder()
				.id(model.getId())
				.user(userDTOMapper.modelToDto(model.getUser()))
				.student(studentDTOMapper.modelToDto(model.getStudent()))
				.build();
	}

}
