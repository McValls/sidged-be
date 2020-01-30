package com.mvalls.sidged.rest.dtos;

import java.time.LocalDate;

import com.mvalls.sidged.model.ClassState;

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
public class CourseClassDTO extends DataTransferObject {
	
	private Long id;
	private Long courseId; 
	private LocalDate date;
	private Integer classNumber;
	private ClassState classState;

}
