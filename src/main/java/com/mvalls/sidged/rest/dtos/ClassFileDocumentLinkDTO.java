package com.mvalls.sidged.rest.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ClassFileDocumentLinkDTO extends DataTransferObject {
	
	private Long classId;
	private String name;
	private String link;

}
