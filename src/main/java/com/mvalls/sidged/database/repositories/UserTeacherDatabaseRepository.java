package com.mvalls.sidged.database.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;
import com.mvalls.sidged.database.dtos.UserTeacherDTO;
import com.mvalls.sidged.database.mybatis.mappers.UserTeacherMapper;
import com.mvalls.sidged.database.repositories.mappers.UserTeacherRepositoryDTOMapper;

public class UserTeacherDatabaseRepository
	implements UserTeacherRepository {
	
	private final UserRepository userRepository;
	private final TeacherRepository teacherRepository;
	private final UserTeacherMapper userTeacherMapper;
	private final UserTeacherRepositoryDTOMapper dtoMapper = new UserTeacherRepositoryDTOMapper();
	
	public UserTeacherDatabaseRepository(UserRepository userRepository,
			TeacherRepository teacherRepository,
			UserTeacherMapper userTeacherMapper) {
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
		this.userTeacherMapper = userTeacherMapper;
	}
	
	@Override
	public UserTeacher findByTeacherId(Long id) {
		UserTeacherDTO dto = this.userTeacherMapper.findByTeacherId(id);
		return dtoMapper.dtoToModel(dto);
	}

	@Override
	public UserTeacher findByUserUsername(String username) {
		UserTeacherDTO dto = this.userTeacherMapper.findByUsername(username);
		return dtoMapper.dtoToModel(dto);
	}

	@Override
	@Transactional
	public UserTeacher create(UserTeacher userTeacher) {
		User user = userTeacher.getUser();
		this.userRepository.create(user);
		
		Teacher teacher = userTeacher.getTeacher();
		this.teacherRepository.create(teacher);
		
		UserTeacherDTO dto = this.dtoMapper.modelToDto(userTeacher);
		this.userTeacherMapper.insert(dto);
		
		return userTeacher;
	}

	@Override
	@Transactional
	public UserTeacher update(UserTeacher userTeacher) {
		this.userRepository.update(userTeacher.getUser());
		this.teacherRepository.update(userTeacher.getTeacher());
		return userTeacher;
	}

}
