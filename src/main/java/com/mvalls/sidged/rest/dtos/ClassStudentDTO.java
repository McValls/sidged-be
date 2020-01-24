package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.StudentPresent;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ClassStudentDTO extends DataTransferObject {
	
	private Long id;
	private StudentPresent present;
	private String names;
	private String lastname;

}
