package com.mvalls.sidged.rest.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StudentDTO extends DataTransferObject {

	private static final long serialVersionUID = -6169150709738508911L;
	
	private Long id;
	private String names;
	private String lastname;
	private String identificationNumber;
	private String legacyNumber;
	private String base64EncodedPic;
	private ContactDataDTO contactData;
	
}
