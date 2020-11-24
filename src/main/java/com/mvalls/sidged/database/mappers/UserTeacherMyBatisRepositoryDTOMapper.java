package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.UserTeacherMyBatisDTO;

public class UserTeacherMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<UserTeacher, UserTeacherMyBatisDTO>{

	private final UserMyBatisRepositoryDTOMapper userDTOMapper = new UserMyBatisRepositoryDTOMapper();
	private final TeacherMyBatisRepositoryDTOMapper teacherDTOMapper = new TeacherMyBatisRepositoryDTOMapper();
	
	@Override
	public UserTeacher dtoToModel(UserTeacherMyBatisDTO dto) {
		return UserTeacher.builder()
				.id(dto.getId())
				.user(userDTOMapper.dtoToModel(dto.getUser()))
				.teacher(teacherDTOMapper.dtoToModel(dto.getTeacher()))
				.build();
	}

	@Override
	public UserTeacherMyBatisDTO modelToDto(UserTeacher model) {
		return UserTeacherMyBatisDTO.builder()
				.id(model.getId())
				.user(userDTOMapper.modelToDto(model.getUser()))
				.teacher(teacherDTOMapper.modelToDto(model.getTeacher()))
				.build();
	}

}
