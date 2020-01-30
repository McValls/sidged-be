package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.PeriodType;
import com.mvalls.sidged.model.Shift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO extends DataTransferObject{
	
	private String name;
	private Shift shift;
	private Integer year;
	private PeriodType periodType;
	private Integer periodNumber;
	private Long timeSinceId;
	private Long timeUntilId;
	private Long careerId;
	private String chair;

}
	