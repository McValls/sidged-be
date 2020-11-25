package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO: Cambiar nombre
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO implements RepositoryDTO {
	
	private Long id;
	private String legacyNumber;
	private String names;
	private String lastname;
	private ContactDataDTO contactData;
	

}
