package com.mvalls.sidged.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mvalls.sidged.valueObjects.ClassFileDocumentVO;

@Component
public class MultipartFileToClassFileDocumentVOMapper extends GenericMapper<MultipartFile, ClassFileDocumentVO>{
	
	@Override
	public ClassFileDocumentVO map(MultipartFile file) {
		try {
			ClassFileDocumentVO classFileVO;
			classFileVO = ClassFileDocumentVO.builder()
					.name(file.getOriginalFilename())
					.content(file.getBytes())
					.contentType(file.getContentType())
					.build();
			return classFileVO;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
