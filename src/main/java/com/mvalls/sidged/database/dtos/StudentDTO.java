package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO implements RepositoryDTO {

	private Long id;
	private String legacyNumber;
	private String names;
	private String lastname;
	private String identificationNumber;
	private ContactDataDTO contactData;
	
}
