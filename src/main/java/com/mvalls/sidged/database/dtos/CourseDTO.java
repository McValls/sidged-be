package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.model.Shift;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO implements RepositoryDTO {
	
	private Long id;
	private String code;
	private String name;
	private Shift shift;
	private Integer year;
	private PeriodDTO period;
	private TimeDTO timeStart;
	private TimeDTO timeEnd;
	private CareerDTO career;

}
