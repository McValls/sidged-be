package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.repositories.ContactDataRepository;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.database.mappers.StudentMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.StudentMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.StudentMapper;

public class StudentDatabaseRepository implements StudentRepository {

	private final ContactDataRepository contactDataRepository;
	private final StudentMapper studentMapper;
	private final StudentMyBatisRepositoryDTOMapper studentMyBatisRepositoryDTOMapper = new StudentMyBatisRepositoryDTOMapper();
	
	public StudentDatabaseRepository(StudentMapper studentMapper,
			ContactDataRepository contactDataRepository) {
		this.studentMapper = studentMapper;
		this.contactDataRepository = contactDataRepository;
	}
	
	@Override
	public List<Student> findByCourseCode(String courseCode) {
		List<StudentMyBatisDTO> dtos = this.studentMapper.findByCourseCode(courseCode);
		return dtos
				.stream()
				.map(studentMyBatisRepositoryDTOMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public void removeCourseStudent(String courseCode, Long studentId) {
		this.studentMapper.removeCourseStudent(courseCode, studentId);
	}
	
	@Override
	public void addCourseStudent(String courseCode, Long studentId) {
		this.studentMapper.addCourseStudent(courseCode, studentId);
	}
	
	@Override
	public Student create(Student student) {
		this.contactDataRepository.create(student.getContactData());
		StudentMyBatisDTO studentDTO = this.studentMyBatisRepositoryDTOMapper.modelToDto(student);
		this.studentMapper.insert(studentDTO);
		student.setId(studentDTO.getId());
		return student;
	}

	@Override
	@Transactional
	public Student update(Student student) {
		StudentMyBatisDTO dto = this.studentMyBatisRepositoryDTOMapper.modelToDto(student);
		this.studentMapper.update(dto);
		this.contactDataRepository.update(student.getContactData());
		return student;
	}

	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> findAll() {
		List<StudentMyBatisDTO> dtos = this.studentMapper.findAll();
		return dtos
				.stream()
				.map(studentMyBatisRepositoryDTOMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
}
