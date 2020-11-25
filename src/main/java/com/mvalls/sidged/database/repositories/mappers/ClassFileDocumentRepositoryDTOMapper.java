package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.ClassFileDocumentDTO;

public class ClassFileDocumentRepositoryDTOMapper implements RepositoryDTOMapper<ClassFileDocument, ClassFileDocumentDTO> {

	@Override
	public ClassFileDocument dtoToModel(ClassFileDocumentDTO dto) {
		return ClassFileDocument.builder()
				.id(dto.getId())
				.name(dto.getName())
				.fileDocumentType(dto.getFileDocumentType())
				.content(dto.getContent())
				.contentType(dto.getContentType())
				.build();
	}

	@Override
	public ClassFileDocumentDTO modelToDto(ClassFileDocument model) {
		return ClassFileDocumentDTO.builder()
				.id(model.getId())
				.name(model.getName())
				.fileDocumentType(model.getFileDocumentType())
				.content(model.getContent())
				.contentType(model.getContentType())
				.courseClassId(model.getCourseClass().getId())
				.build();
	}

}
