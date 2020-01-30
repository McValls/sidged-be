package com.mvalls.sidged.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAllDTO extends DataTransferObject {

	private static final long serialVersionUID = 2361544748686259329L;

	private Long id;
	private String username;
	private String legacyNumber;
	private String names;
	private String lastname;
	private ContactDataDTO contactData;

}
