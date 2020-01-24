package com.mvalls.sidged.mappers;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.ClassFileDocument;
import com.mvalls.sidged.model.FileDocumentType;
import com.mvalls.sidged.rest.dtos.ClassFileDocumentDTO;

@Component
public class ClassFileDocumentModelToDTOMapper extends GenericMapper<ClassFileDocument, ClassFileDocumentDTO>{

	@Value("${server.port}") int serverPort;
	
	@Override
	public ClassFileDocumentDTO map(ClassFileDocument model) {
		return ClassFileDocumentDTO.builder()
				.id(model.getId())
				.fileDocumentType(model.getFileDocumentType())
				.name(model.getName())
				.size(model.getContent().length)
				.linkContent(getLinkContent(model))
				.contentType(getContentType(model))
				.build();
				
	}
	

	private String getLinkContent(ClassFileDocument fileDocument) {
		if(fileDocument.getFileDocumentType() == FileDocumentType.LINK) {
			return new String(fileDocument.getContent());
		} else {
			StringBuilder sBuilder = new StringBuilder("http://");
			sBuilder.append(InetAddress.getLoopbackAddress().getHostName());
			if(serverPort > 0) {
				sBuilder.append(":").append(serverPort);
			}
			sBuilder.append("/file-documents/file/"+fileDocument.getId());
			return sBuilder.toString();
		}
	}
	
	private String getContentType(ClassFileDocument model) {
		if(model.getFileDocumentType() == FileDocumentType.BLOB) {
			return model.getContentType();
		} else {
			return "text";
		}
	}
}
