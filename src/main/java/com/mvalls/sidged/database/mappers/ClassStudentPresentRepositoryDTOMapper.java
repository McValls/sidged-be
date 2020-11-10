package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.ClassStudentPresentDTO;
import com.mvalls.sidged.database.dtos.StudentPresentDTO;

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
		return ClassStudentPresentDTO.builder()
				.id(model.getId())
				.student(studentDTOMapper.modelToDto(model.getStudent()))
				.present(StudentPresentDTO.valueOf(model.getPresent().name()))
				.build();
	}

}
