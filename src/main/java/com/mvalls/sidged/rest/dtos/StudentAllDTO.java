package com.mvalls.sidged.rest.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class StudentAllDTO extends DataTransferObject {

	private static final long serialVersionUID = -5169104870907842257L;

	private Long id;
	private String legacyNumber;
	private String names;
	private String lastname;
	private String identificationNumber;
	private ContactDataDTO contactData;
	
}
