package com.mvalls.sidged.valueObjects;

import com.mvalls.sidged.model.PeriodType;
import com.mvalls.sidged.model.Shift;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseVO {
	
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
