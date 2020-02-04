package com.mvalls.sidged.model.analytics;

import java.util.List;

import lombok.Data;

@Data
public class PresentismAnalysisData {
	
	private Long courseId;
	private String courseName;
	private Integer courseYear;
	private Integer numberOfClasses;
	private List<PresentPercentages> percentagesByClassNumber;
	private List<PercentageByStudentPresent> percentagesByStudentPresent;

}
