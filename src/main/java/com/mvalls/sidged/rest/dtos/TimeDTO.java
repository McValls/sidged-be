package com.mvalls.sidged.rest.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class TimeDTO extends DataTransferObject {
	
	private static final long serialVersionUID = 8840667148818196905L;

	private Long id;
	private String since;
	private String until;
	
}
