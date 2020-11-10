package com.mvalls.sidged.database.mappers;

import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.CourseClassDTO;

public class CourseClassRepositoryDTOMapper implements RepositoryDTOMapper<CourseClass, CourseClassDTO>{

	private final ClassStudentPresentRepositoryDTOMapper classStudentPresentDTOMapper = new ClassStudentPresentRepositoryDTOMapper();
	private final ClassFileDocumentRepositoryDTOMapper classFileDocumentDTOMapper = new ClassFileDocumentRepositoryDTOMapper();
	
	@Override
	public CourseClass dtoToModel(CourseClassDTO dto) {
		return CourseClass.builder()
				.id(dto.getId())
				.classNumber(dto.getClassNumber())
				.date(dto.getDate())
				.classState(dto.getClassState())
				.studentPresents(dto.getStudentPresents()
						.stream()
						.map(classStudentPresentDTOMapper::dtoToModel)
						.collect(Collectors.toList()))
				.classFileDocuments(dto.getClassFileDocuments()
						.stream()
						.map(classFileDocumentDTOMapper::dtoToModel)
						.collect(Collectors.toList()))
				.comments(dto.getComments())
				.build();
	}
	
	@Override
	public CourseClassDTO modelToDto(CourseClass model) {
		return CourseClassDTO.builder()
				.id(model.getId())
				.classNumber(model.getClassNumber())
				.date(model.getDate())
				.classState(model.getClassState())
				.studentPresents(model.getStudentPresents()
						.stream()
						.map(classStudentPresentDTOMapper::modelToDto)
						.collect(Collectors.toList()))
				.classFileDocuments(model.getClassFileDocuments()
						.stream()
						.map(classFileDocumentDTOMapper::modelToDto)
						.collect(Collectors.toList()))
				.comments(model.getComments())
				.build();
	}
	
}
