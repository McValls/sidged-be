package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.Shift;

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
public class CourseListDTO extends DataTransferObject {

	private static final long serialVersionUID = 4270342625717971366L;

	private Long id;
	private String name;
	private Shift shift;
	private Integer year;
	private PeriodDTO period;
	private String timeSince;
	private String timeUntil;
	private String career;
	
}
