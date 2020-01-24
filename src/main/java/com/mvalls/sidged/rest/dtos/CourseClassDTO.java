package com.mvalls.sidged.rest.dtos;

import java.time.LocalDate;

import com.mvalls.sidged.model.ClassState;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CourseClassDTO extends DataTransferObject {
	
	private Long id;
	private Long courseId; 
	private LocalDate date;
	private Integer classNumber;
	private ClassState classState;

}
