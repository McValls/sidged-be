package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.StudentPresentDTO;
import com.mvalls.sidged.database.mybatis.dtos.ClassStudentPresentMyBatisDTO;

public class ClassStudentPresentRepositoryDTOMapper implements RepositoryDTOMapper<ClassStudentPresent, ClassStudentPresentMyBatisDTO> {

	private final StudentRepositoryDTOMapper studentDTOMapper = new StudentRepositoryDTOMapper();
	
	@Override
	public ClassStudentPresent dtoToModel(ClassStudentPresentMyBatisDTO dto) {
		return ClassStudentPresent.builder()
				.id(dto.getId())
//				.student(studentDTOMapper.dtoToModel(dto.getStudent()))
				.present(StudentPresent.valueOf(dto.getPresent().name()))
				.build();
	}

	@Override
	public ClassStudentPresentMyBatisDTO modelToDto(ClassStudentPresent model) {
//		return ClassStudentPresentDTO.builder()
//				.id(model.getId())
//				.student(studentDTOMapper.modelToDto(model.getStudent()))
//				.present(StudentPresentDTO.valueOf(model.getPresent().name()))
//				.build();
		return null;
	}

}
