package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.PeriodType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PeriodDTO extends DataTransferObject{

	private static final long serialVersionUID = 3755554575690540461L;

	private PeriodType periodType;
	private Integer number;
	
}
