package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.StudentPresent;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class StudentPresentDTO extends DataTransferObject {
	
	private StudentPresent present;

}
