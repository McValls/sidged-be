package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.UserStudentDTO;

public class UserStudentRepositoryDTOMapper implements RepositoryDTOMapper<UserStudent, UserStudentDTO>{

	private final UserRepositoryDTOMapper userDTOMapper = new UserRepositoryDTOMapper();
	private final StudentRepositoryDTOMapper studentDTOMapper = new StudentRepositoryDTOMapper();
	
	@Override
	public UserStudent dtoToModel(UserStudentDTO dto) {
		return UserStudent.builder()
				.id(dto.getId())
				.user(userDTOMapper.dtoToModel(dto.getUser()))
				.student(studentDTOMapper.dtoToModel(dto.getStudent()))
				.build();
	}

	@Override
	public UserStudentDTO modelToDto(UserStudent model) {
		return UserStudentDTO.builder()
				.id(model.getId())
				.user(userDTOMapper.modelToDto(model.getUser()))
				.student(studentDTOMapper.modelToDto(model.getStudent()))
				.build();
	}

}
