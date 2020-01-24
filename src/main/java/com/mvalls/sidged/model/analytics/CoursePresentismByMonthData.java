package com.mvalls.sidged.model.analytics;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePresentismByMonthData {

	private Integer month;
	private List<PercentageByStudentPresent> percentagesByMonth = new ArrayList<>();
	
}
