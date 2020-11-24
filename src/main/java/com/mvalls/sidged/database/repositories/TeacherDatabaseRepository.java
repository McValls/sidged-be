package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.ContactDataRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.database.mappers.TeacherMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.TeacherMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.TeacherMapper;

public class TeacherDatabaseRepository implements TeacherRepository {

	private final TeacherMapper teacherMapper;
	private final ContactDataRepository contactDataRepository;
	private final TeacherMyBatisRepositoryDTOMapper dtoMyBatisMapper;
	
	public TeacherDatabaseRepository(TeacherMapper teacherMapper,
			ContactDataRepository contactDataRepository) {
		this.teacherMapper = teacherMapper;
		this.contactDataRepository = contactDataRepository;
		this.dtoMyBatisMapper = new TeacherMyBatisRepositoryDTOMapper();
	}
	

	@Override
	public List<Teacher> findByCourseCode(String courseCode) {
		List<TeacherMyBatisDTO> dtos = this.teacherMapper.findByCourseCode(courseCode);
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
		List<TeacherMyBatisDTO> dtos = this.teacherMapper.findAll();
		return dtos
				.stream()
				.map(this.dtoMyBatisMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public Teacher update(Teacher teacher) {
		TeacherMyBatisDTO dto = this.dtoMyBatisMapper.modelToDto(teacher);
		this.teacherMapper.update(dto);
		this.contactDataRepository.update(teacher.getContactData());
		return teacher;
	}


	@Override
	public Teacher create(Teacher teacher) {
		this.contactDataRepository.create(teacher.getContactData());
		
		TeacherMyBatisDTO dto = this.dtoMyBatisMapper.modelToDto(teacher);
		this.teacherMapper.insert(dto);
		teacher.setId(dto.getId());
		return teacher;
	}


	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Teacher findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
