package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.StudentLink;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.StudentLinkDTO;

public class StudentLinkRepositoryDTOMapper implements RepositoryDTOMapper<StudentLink, StudentLinkDTO>{

	@Override
	public StudentLink dtoToModel(StudentLinkDTO dto) {
		return StudentLink.builder()
				.id(dto.getId())
				.link(dto.getLink())
				.title(dto.getTitle())
				.build();
	}

	@Override
	public StudentLinkDTO modelToDto(StudentLink model) {
		return StudentLinkDTO.builder()
				.id(model.getId())
				.link(model.getLink())
				.title(model.getTitle())
				.build();
	}

}
