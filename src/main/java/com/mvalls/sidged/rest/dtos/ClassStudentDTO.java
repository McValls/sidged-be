package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.model.StudentPresent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassStudentDTO extends DataTransferObject {
	
	private Long id;
	private StudentPresent present;
	private String names;
	private String lastname;

}
