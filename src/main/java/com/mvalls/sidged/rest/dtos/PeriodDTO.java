package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.PeriodType;

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
public class PeriodDTO extends DataTransferObject{

	private static final long serialVersionUID = 3755554575690540461L;

	private PeriodType periodType;
	private Integer number;
	
}
