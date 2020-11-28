package com.mvalls.sidged.rest.mappers;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.model.FileDocumentType;
import com.mvalls.sidged.rest.dtos.ClassFileDocumentDTO;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
public class ClassFileDocumentModelToDTOMapper {

	private final String serverHost;
	
	public ClassFileDocumentModelToDTOMapper(String serverHost) {
		super();
		this.serverHost = serverHost;
	}

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
			StringBuilder sBuilder = new StringBuilder(serverHost);
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
