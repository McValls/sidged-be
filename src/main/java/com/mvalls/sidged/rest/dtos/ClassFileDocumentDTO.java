package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.FileDocumentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassFileDocumentDTO extends DataTransferObject {
	
	private Long id;
	private String name;
	private String linkContent;
	private Integer size;
	private FileDocumentType fileDocumentType;
	private String contentType;

}
