package com.mvalls.sidged.valueObjects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassFileDocumentVO {
	
	private String name;
	private String link;
	private byte[] content;
	private String contentType;
	private Long classId;

}
