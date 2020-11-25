package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.model.PeriodType;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodDTO implements RepositoryDTO {

	private Long id;
	private PeriodType periodType;
	private Integer number;
	
}
