package com.mvalls.sidged.database.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;
import com.mvalls.sidged.database.mappers.UserTeacherMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.UserTeacherMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.UserTeacherMapper;

public class UserTeacherDatabaseRepository
	implements UserTeacherRepository {
	
	private final UserRepository userRepository;
	private final TeacherRepository teacherRepository;
	private final UserTeacherMapper userTeacherMapper;
	private final UserTeacherMyBatisRepositoryDTOMapper dtoMapper = new UserTeacherMyBatisRepositoryDTOMapper();
	
	public UserTeacherDatabaseRepository(UserRepository userRepository,
			TeacherRepository teacherRepository,
			UserTeacherMapper userTeacherMapper) {
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
		this.userTeacherMapper = userTeacherMapper;
	}
	
	@Override
	public UserTeacher findByTeacherId(Long id) {
		UserTeacherMyBatisDTO dto = this.userTeacherMapper.findByTeacherId(id);
		return dtoMapper.dtoToModel(dto);
	}

	@Override
	public UserTeacher findByUserUsername(String username) {
		UserTeacherMyBatisDTO dto = this.userTeacherMapper.findByUsername(username);
		return dtoMapper.dtoToModel(dto);
	}

	@Override
	@Transactional
	public UserTeacher create(UserTeacher userTeacher) {
		User user = userTeacher.getUser();
		this.userRepository.create(user);
		
		Teacher teacher = userTeacher.getTeacher();
		this.teacherRepository.create(teacher);
		
		UserTeacherMyBatisDTO dto = this.dtoMapper.modelToDto(userTeacher);
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

	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserTeacher findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserTeacher> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
