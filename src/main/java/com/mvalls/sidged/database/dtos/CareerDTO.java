package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareerDTO implements RepositoryDTO {
	
	private Long id;
	private String code;
	private String name;

}
