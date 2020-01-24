package com.mvalls.sidged.rest.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CareerDTO extends DataTransferObject {
	
	private Long id;
	private String name;

}
