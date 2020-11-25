package com.mvalls.sidged.database.repositories;


import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserStudentRepository;
import com.mvalls.sidged.database.dtos.UserStudentDTO;
import com.mvalls.sidged.database.mybatis.mappers.UserStudentMapper;
import com.mvalls.sidged.database.repositories.mappers.UserStudentRepositoryDTOMapper;

public class UserStudentDatabaseRepository implements UserStudentRepository {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final UserStudentMapper userStudentMapper;
	private final UserStudentRepositoryDTOMapper myBatisDtoMapper = new UserStudentRepositoryDTOMapper();

	public UserStudentDatabaseRepository(UserRepository userRepository,
			StudentRepository studentRepository,
			UserStudentMapper userStudentMapper) {
		this.userStudentMapper = userStudentMapper;
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public UserStudent findByUserUsername(String username) {
		Optional<UserStudentDTO> dto = this.userStudentMapper.findByUserUsername(username);
		return dto.map(myBatisDtoMapper::dtoToModel).orElseThrow();
	}

	@Override
	public UserStudent findByStudentId(Long id) {
		Optional<UserStudentDTO> dto = this.userStudentMapper.findByStudentId(id);
		return dto.map(myBatisDtoMapper::dtoToModel).orElseThrow();
	}

	@Override
	@Transactional
	public UserStudent create(UserStudent userStudent) {
		User user = userStudent.getUser();
		this.userRepository.create(user);
		
		Student student = userStudent.getStudent();
		this.studentRepository.create(student);
		
		UserStudentDTO userStudentDTO = this.myBatisDtoMapper.modelToDto(userStudent);
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

}
