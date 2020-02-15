package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.StudentLink;
import com.mvalls.sidged.rest.dtos.StudentLinkDTO;

@Component
public class StudentLinkMapper extends GenericMapper<StudentLink, StudentLinkDTO>{

	@Override
	public StudentLinkDTO map(StudentLink model) {
		StudentLinkDTO dto = StudentLinkDTO.builder()
				.id(model.getId())
				.link(model.getLink())
				.title(model.getTitle())
				.build();
		return dto;
	}
	
}
