package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.rest.dtos.ClassFileDocumentLinkDTO;
import com.mvalls.sidged.valueObjects.ClassFileDocumentVO;

@Component
public class ClassFileDocumentDTOToVOMapper extends GenericMapper<ClassFileDocumentLinkDTO, ClassFileDocumentVO> {

	@Override
	public ClassFileDocumentVO map(ClassFileDocumentLinkDTO file) {
		ClassFileDocumentVO classFileVO;
		classFileVO = ClassFileDocumentVO.builder()
				.name(file.getName())
				.classId(file.getClassId())
				.link(file.getLink())
				.contentType("Link")
				.build();
		return classFileVO;
	}

}
