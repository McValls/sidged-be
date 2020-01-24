package com.mvalls.sidged.valueObjects;

import com.mvalls.sidged.model.StudentPresent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassUpdatePresentVO {
	
	private Long courseId;
	private Long classId;
	private Long studentId;
	private StudentPresent present;

}
