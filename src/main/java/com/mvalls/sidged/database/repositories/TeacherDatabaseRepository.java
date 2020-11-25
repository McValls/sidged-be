package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.ContactDataRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.database.dtos.TeacherDTO;
import com.mvalls.sidged.database.mybatis.mappers.TeacherMapper;
import com.mvalls.sidged.database.repositories.mappers.TeacherRepositoryDTOMapper;

public class TeacherDatabaseRepository implements TeacherRepository {

	private final TeacherMapper teacherMapper;
	private final ContactDataRepository contactDataRepository;
	private final TeacherRepositoryDTOMapper dtoMyBatisMapper;
	
	public TeacherDatabaseRepository(TeacherMapper teacherMapper,
			ContactDataRepository contactDataRepository) {
		this.teacherMapper = teacherMapper;
		this.contactDataRepository = contactDataRepository;
		this.dtoMyBatisMapper = new TeacherRepositoryDTOMapper();
	}
	

	@Override
	public List<Teacher> findByCourseCode(String courseCode) {
		List<TeacherDTO> dtos = this.teacherMapper.findByCourseCode(courseCode);
		return dtos
				.stream()
				.map(dtoMyBatisMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public void removeCourseTeacher(String courseCode, Long teacherId) {
		this.teacherMapper.removeCourseTeacher(courseCode, teacherId);
	}
	
	@Override
	public void addCourseTeacher(String courseCode, Long teacherId) {
		this.teacherMapper.addCourseTeacher(courseCode, teacherId);
	}
	
	@Override
	public List<Teacher> findAll() {
		List<TeacherDTO> dtos = this.teacherMapper.findAll();
		return dtos
				.stream()
				.map(this.dtoMyBatisMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public Teacher update(Teacher teacher) {
		TeacherDTO dto = this.dtoMyBatisMapper.modelToDto(teacher);
		this.teacherMapper.update(dto);
		this.contactDataRepository.update(teacher.getContactData());
		return teacher;
	}


	@Override
	public Teacher create(Teacher teacher) {
		this.contactDataRepository.create(teacher.getContactData());
		
		TeacherDTO dto = this.dtoMyBatisMapper.modelToDto(teacher);
		this.teacherMapper.insert(dto);
		teacher.setId(dto.getId());
		return teacher;
	}

}
