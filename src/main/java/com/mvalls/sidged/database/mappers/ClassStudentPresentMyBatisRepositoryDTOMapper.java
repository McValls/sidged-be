package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.ClassStudentPresentMyBatisDTO;

public class ClassStudentPresentMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<ClassStudentPresent, ClassStudentPresentMyBatisDTO> {

	private final StudentMyBatisRepositoryDTOMapper studentDTOMapper = new StudentMyBatisRepositoryDTOMapper();
	
	@Override
	public ClassStudentPresent dtoToModel(ClassStudentPresentMyBatisDTO dto) {
		return ClassStudentPresent.builder()
				.id(dto.getId())
				.student(studentDTOMapper.dtoToModel(dto.getStudent()))
				.present(StudentPresent.valueOf(dto.getPresent().name()))
				.build();
	}

	@Override
	public ClassStudentPresentMyBatisDTO modelToDto(ClassStudentPresent model) {
		CourseClassRepositoryDTOMapper courseClassDTOMapper = new CourseClassRepositoryDTOMapper();
		return ClassStudentPresentMyBatisDTO.builder()
				.id(model.getId())
				.student(studentDTOMapper.modelToDto(model.getStudent()))
				.present(StudentPresent.valueOf(model.getPresent().name()))
				.courseClass(courseClassDTOMapper.modelToDto(model.getCourseClass()))
				.build();
	}

}
