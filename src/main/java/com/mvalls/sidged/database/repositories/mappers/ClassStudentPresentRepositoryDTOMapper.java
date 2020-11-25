package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.ClassStudentPresentDTO;

public class ClassStudentPresentRepositoryDTOMapper implements RepositoryDTOMapper<ClassStudentPresent, ClassStudentPresentDTO> {

	private final StudentRepositoryDTOMapper studentDTOMapper = new StudentRepositoryDTOMapper();
	
	@Override
	public ClassStudentPresent dtoToModel(ClassStudentPresentDTO dto) {
		return ClassStudentPresent.builder()
				.id(dto.getId())
				.student(studentDTOMapper.dtoToModel(dto.getStudent()))
				.present(StudentPresent.valueOf(dto.getPresent().name()))
				.build();
	}

	@Override
	public ClassStudentPresentDTO modelToDto(ClassStudentPresent model) {
		CourseClassRepositoryDTOMapper courseClassDTOMapper = new CourseClassRepositoryDTOMapper();
		return ClassStudentPresentDTO.builder()
				.id(model.getId())
				.student(studentDTOMapper.modelToDto(model.getStudent()))
				.present(StudentPresent.valueOf(model.getPresent().name()))
				.courseClass(courseClassDTOMapper.modelToDto(model.getCourseClass()))
				.build();
	}

}
