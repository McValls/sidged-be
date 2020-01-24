package com.mvalls.sidged.model.analytics;

import java.util.Map;

import com.mvalls.sidged.model.StudentPresent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PresentPercentages {

	private Integer classNumber;
	private Map<StudentPresent, Double> presentPercentage;
	
}
