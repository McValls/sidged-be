package com.mvalls.sidged.rest.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class TeacherAllDTO extends DataTransferObject {

	private static final long serialVersionUID = 2361544748686259329L;

	private Long id;
	private String legacyNumber;
	private String names;
	private String lastname;
	private ContactDataDTO contactData;

}
