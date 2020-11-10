package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.login.UserTeacherDTO;

public class UserTeacherRepositoryDTOMapper implements RepositoryDTOMapper<UserTeacher, UserTeacherDTO>{

	private final UserRepositoryDTOMapper userDTOMapper = new UserRepositoryDTOMapper();
	private final TeacherRepositoryDTOMapper teacherDTOMapper = new TeacherRepositoryDTOMapper();
	
	@Override
	public UserTeacher dtoToModel(UserTeacherDTO dto) {
		return UserTeacher.builder()
				.id(dto.getId())
				.user(userDTOMapper.dtoToModel(dto.getUser()))
				.teacher(teacherDTOMapper.dtoToModel(dto.getTeacher()))
				.build();
	}

	@Override
	public UserTeacherDTO modelToDto(UserTeacher model) {
		return UserTeacherDTO.builder()
				.id(model.getId())
				.user(userDTOMapper.modelToDto(model.getUser()))
				.teacher(teacherDTOMapper.modelToDto(model.getTeacher()))
				.build();
	}

}
