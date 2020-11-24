package com.mvalls.sidged.database.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserStudentRepository;
import com.mvalls.sidged.database.mappers.UserStudentMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.UserStudentMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.UserStudentMapper;

public class UserStudentDatabaseRepository implements UserStudentRepository {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final UserStudentMapper userStudentMapper;
	private final UserStudentMyBatisRepositoryDTOMapper myBatisDtoMapper = new UserStudentMyBatisRepositoryDTOMapper();

	public UserStudentDatabaseRepository(UserRepository userRepository,
			StudentRepository studentRepository,
			UserStudentMapper userStudentMapper) {
		this.userStudentMapper = userStudentMapper;
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public UserStudent findByUserUsername(String username) {
		Optional<UserStudentMyBatisDTO> dto = this.userStudentMapper.findByUserUsername(username);
		return dto.map(myBatisDtoMapper::dtoToModel).orElseThrow();
	}

	@Override
	public UserStudent findByStudentId(Long id) {
		Optional<UserStudentMyBatisDTO> dto = this.userStudentMapper.findByStudentId(id);
		return dto.map(myBatisDtoMapper::dtoToModel).orElseThrow();
	}

	@Override
	@Transactional
	public UserStudent create(UserStudent userStudent) {
		User user = userStudent.getUser();
		this.userRepository.create(user);
		
		Student student = userStudent.getStudent();
		this.studentRepository.create(student);
		
		UserStudentMyBatisDTO userStudentDTO = this.myBatisDtoMapper.modelToDto(userStudent);
		this.userStudentMapper.insert(userStudentDTO);
		return userStudent;
	}

	@Override
	@Transactional
	public UserStudent update(UserStudent userStudent) {
		User user = userStudent.getUser();
		this.userRepository.update(user);
		
		Student student = userStudent.getStudent();
		this.studentRepository.update(student);
		
		return userStudent;
	}

	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserStudent findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserStudent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
