package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.database.mappers.ClassStudentPresentMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.ClassStudentPresentMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.ClassStudentPresentMapper;
import com.mvalls.sidged.database.repositories.jpa.ClassStudentPresentJpaRepository;

public class ClassStudentPresentDatabaseRepository implements ClassStudentPresentRepository {

	private final ClassStudentPresentMapper classStudentPresentMapper;
	private final ClassStudentPresentMyBatisRepositoryDTOMapper dtoMapper = new ClassStudentPresentMyBatisRepositoryDTOMapper();

	public ClassStudentPresentDatabaseRepository(ClassStudentPresentJpaRepository jpaRepository,
			ClassStudentPresentMapper classStudentPresentMapper) {
		this.classStudentPresentMapper = classStudentPresentMapper;
	}
	
	@Override
	public List<ClassStudentPresent> findByStudentId(Long studentId) {
//		return this.jpaRepository.findByStudentId(studentId)
//				.stream()
//				.map(this.dtoMapper::dtoToModel)
//				.collect(Collectors.toList());
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Optional<ClassStudentPresent> findByCourseCodeAndClassNumberAndStudentId(
			String courseCode, Integer classNumber, Long studentId) {
		Optional<ClassStudentPresentMyBatisDTO> dto = 
				this.classStudentPresentMapper.findByCourseCodeClassNumberAndStudentId(courseCode, classNumber, studentId);
		return dto.map(dtoMapper::dtoToModel);
	}
	
	@Override
	public List<ClassStudentPresent> findByCourseAndClassNumber(String courseCode, Integer classNumber) {
		List<ClassStudentPresentMyBatisDTO> dtos = this.classStudentPresentMapper.findByCourseAndClassNumber(courseCode, classNumber);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	@Override
	public ClassStudentPresent create(ClassStudentPresent classStudentPresent) {
		ClassStudentPresentMyBatisDTO dto = dtoMapper.modelToDto(classStudentPresent);
		classStudentPresentMapper.insert(dto);
		return classStudentPresent;
	}

	@Override
	public ClassStudentPresent update(ClassStudentPresent classStudentPresent) {
		classStudentPresentMapper.update(classStudentPresent.getId(), classStudentPresent.getPresent());
		return classStudentPresent;
	}

	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClassStudentPresent findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassStudentPresent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
