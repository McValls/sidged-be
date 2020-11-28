package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.database.dtos.ClassStudentPresentDTO;
import com.mvalls.sidged.database.mybatis.mappers.ClassStudentPresentMapper;
import com.mvalls.sidged.database.repositories.mappers.ClassStudentPresentRepositoryDTOMapper;

public class ClassStudentPresentDatabaseRepository implements ClassStudentPresentRepository {

	private final ClassStudentPresentMapper classStudentPresentMapper;
	private final ClassStudentPresentRepositoryDTOMapper dtoMapper = new ClassStudentPresentRepositoryDTOMapper();

	public ClassStudentPresentDatabaseRepository(ClassStudentPresentMapper classStudentPresentMapper) {
		this.classStudentPresentMapper = classStudentPresentMapper;
	}
	
	@Override
	public Optional<ClassStudentPresent> findByCourseCodeAndClassNumberAndStudentId(
			String courseCode, Integer classNumber, Long studentId) {
		Optional<ClassStudentPresentDTO> dto = 
				this.classStudentPresentMapper.findByCourseCodeClassNumberAndStudentId(courseCode, classNumber, studentId);
		return dto.map(dtoMapper::dtoToModel);
	}
	
	@Override
	public List<ClassStudentPresent> findByCourseAndClassNumber(String courseCode, Integer classNumber) {
		List<ClassStudentPresentDTO> dtos = this.classStudentPresentMapper.findByCourseAndClassNumber(courseCode, classNumber);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	@Override
	public ClassStudentPresent create(ClassStudentPresent classStudentPresent) {
		ClassStudentPresentDTO dto = dtoMapper.modelToDto(classStudentPresent);
		classStudentPresentMapper.insert(dto);
		return classStudentPresent;
	}

	@Override
	public ClassStudentPresent update(ClassStudentPresent classStudentPresent) {
		classStudentPresentMapper.update(classStudentPresent.getId(), classStudentPresent.getPresent());
		return classStudentPresent;
	}

}
