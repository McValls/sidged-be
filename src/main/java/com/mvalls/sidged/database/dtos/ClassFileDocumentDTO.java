package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.model.FileDocumentType;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassFileDocumentDTO implements RepositoryDTO {
	
	private Long id;
	private String name;
	private FileDocumentType fileDocumentType;
	private byte[] content;
	private String contentType;
	private Long courseClassId;
	
}
