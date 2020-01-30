package com.mvalls.sidged.rest.dtos;

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
public class TimeDTO extends DataTransferObject {
	
	private static final long serialVersionUID = 8840667148818196905L;

	private Long id;
	private String since;
	private String until;
	
}
