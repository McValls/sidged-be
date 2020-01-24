package com.mvalls.sidged.model.analytics;

import com.mvalls.sidged.model.StudentPresent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PercentageByStudentPresent {
	
	private StudentPresent studentPresent;
	private Integer percentage;

}
