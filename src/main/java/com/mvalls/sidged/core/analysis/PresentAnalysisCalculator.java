package com.mvalls.sidged.core.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.analytics.CoursePresentismByMonthData;
import com.mvalls.sidged.core.model.analytics.CoursePresentismData;
import com.mvalls.sidged.core.model.analytics.PercentageByStudentPresent;
import com.mvalls.sidged.core.model.analytics.PresentPercentages;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
public class PresentAnalysisCalculator {
	
	public CoursePresentismData getPresentismByCourseGroupedByMonth(Course course) {
		Map<Integer, List<CourseClass>> classesByMonth = 
				course.getClasses().stream().collect(Collectors.groupingBy(cc -> cc.getDate().getMonth().getValue()));
		
		CoursePresentismData presentismData = new CoursePresentismData();
		fillPresentismDataGroupedByMonth(classesByMonth, presentismData);
		fillPresentismDataTotalAveragesPercentages(course.getClasses(), presentismData);
		
		return presentismData;
		
	}
	
	private void fillPresentismDataGroupedByMonth(Map<Integer, List<CourseClass>> classesByMonth,
			CoursePresentismData presentismData) {
		classesByMonth.forEach((month, classes) -> {
			Long presents = classes.stream()
				.map(courseClass -> courseClass.getStudentPresents())
				.flatMap(Collection::stream)
				.filter(classStudentPresent -> classStudentPresent.getPresent() == StudentPresent.PRESENT)
				.count();
			
			Long absents = classes.stream()
					.map(courseClass -> courseClass.getStudentPresents())
					.flatMap(Collection::stream)
					.filter(classStudentPresent -> classStudentPresent.getPresent() == StudentPresent.ABSENT)
					.count();
				
			Long lates = classes.stream()
						.map(courseClass -> courseClass.getStudentPresents())
					.flatMap(Collection::stream)
					.filter(classStudentPresent -> classStudentPresent.getPresent() == StudentPresent.LATE)
					.count();
			
			Long total = presents + absents + lates;
			
			
			List<PercentageByStudentPresent> averages = new ArrayList<>();
			
			if(total > 0) {
				Integer presentAverage = (int) (presents * 100 / total);
				Integer absentAverage = (int) (absents * 100 / total);
				Integer lateAverage = (int) (lates * 100 / total);
				averages.add(new PercentageByStudentPresent(StudentPresent.PRESENT, presentAverage));
				averages.add(new PercentageByStudentPresent(StudentPresent.ABSENT, absentAverage));
				averages.add(new PercentageByStudentPresent(StudentPresent.LATE, lateAverage));
				
				presentismData.getPresentismByMonth().add(new CoursePresentismByMonthData(month, averages));
			}
			
		});
	}
	
	private void fillPresentismDataTotalAveragesPercentages(Collection<CourseClass> courseClasses, CoursePresentismData presentismData) {
		List<ClassStudentPresent> classStudentPresents = new ArrayList<>();
		for(CourseClass courseClass : courseClasses) {
			classStudentPresents.addAll(courseClass.getStudentPresents());
		}
		int total = 0;
		int presents = 0;
		int lates = 0;
		int absents = 0;
		for(ClassStudentPresent classStudentPresent : classStudentPresents) {
			switch(classStudentPresent.getPresent()) {
				case PRESENT:
					presents++;
					break;
				case LATE:
					lates++;
					break;
				case ABSENT:
					absents++;
					break;
			}
			total++;
		}
		
		presentismData.getTotalAveragesPercentages()
			.add(new PercentageByStudentPresent(StudentPresent.PRESENT, this.getPercentage(total, presents).intValue()));
		presentismData.getTotalAveragesPercentages()
			.add(new PercentageByStudentPresent(StudentPresent.LATE, this.getPercentage(total, lates).intValue()));
		presentismData.getTotalAveragesPercentages()
			.add(new PercentageByStudentPresent(StudentPresent.ABSENT, this.getPercentage(total, absents).intValue()));
	}

	public List<PresentPercentages> getPresentPercentagesByClasses(Course course) {
		List<PresentPercentages> presentPercentagesByClasses = new LinkedList<>();
		Iterator<CourseClass> courseClassIterator = course.getClasses().iterator();
		int i = 1;
		while(courseClassIterator.hasNext()) {
			PresentPercentages percentage = PresentPercentages.builder()
					.classNumber(i++)
					.presentPercentage(getPresentPercentage(courseClassIterator.next()))
					.build();
			presentPercentagesByClasses.add(percentage);
		}
		return presentPercentagesByClasses;
	}
	
	public Map<StudentPresent, Double> getPresentPercentage(CourseClass courseClass) {
		Collection<ClassStudentPresent> studentPresents = courseClass.getStudentPresents();
		int presents = 0, absents = 0, lates = 0;
		for(ClassStudentPresent studentPresent: studentPresents) {
			switch (studentPresent.getPresent()) {
			case PRESENT:
				presents++;
				break;
			case ABSENT:
				absents++;
				break;
			case LATE:
				lates++;
				break;
			}
		}
		int total = studentPresents.size();
		
		Map<StudentPresent, Double> presentPercentage = new HashMap<>();
		presentPercentage.put(StudentPresent.PRESENT, getPercentage(total, presents));
		presentPercentage.put(StudentPresent.ABSENT, getPercentage(total, absents));
		presentPercentage.put(StudentPresent.LATE, getPercentage(total, lates));
		
		return presentPercentage;
	}
	
	private Double getPercentage(int total, int value) {
		double percentage = 0.0;
		if(value != 0) {
			percentage = (value * 100) / (double) total;
		}
		return percentage;
	}
	
	public List<PresentismAnalysisData> getPresentismByStudentGroupedByCourse(Map<Course, List<ClassStudentPresent>> presentismGroupedByCourse) {
		List<PresentismAnalysisData> analysisData = new ArrayList<>();
		
		for(Entry<Course, List<ClassStudentPresent>> listPresentsByCourse: presentismGroupedByCourse.entrySet()) {
			List<ClassStudentPresent> listPresents = listPresentsByCourse.getValue();
			int total = listPresents.size();
			int presents = 0, lates = 0, absents = 0;
			for(ClassStudentPresent classStudentPresent : listPresents) {
				switch(classStudentPresent.getPresent()) {
					case PRESENT:
						presents++;
						break;
					case LATE:
						lates++;
						break;
					case ABSENT:
						absents++;
						break;
				}
			}
			PresentismAnalysisData presentismAnalysisData = new PresentismAnalysisData();
			presentismAnalysisData.setCourseCode(listPresentsByCourse.getKey().getCode());
			presentismAnalysisData.setCourseName(listPresentsByCourse.getKey().getName());
			presentismAnalysisData.setNumberOfClasses(total);
			presentismAnalysisData.setCourseYear(listPresentsByCourse.getKey().getYear());
			
			PercentageByStudentPresent presentsPercentage = new PercentageByStudentPresent(StudentPresent.PRESENT, getPercentage(total, presents).intValue());
			PercentageByStudentPresent latesPercentage = new PercentageByStudentPresent(StudentPresent.LATE, getPercentage(total, lates).intValue());
			PercentageByStudentPresent absentsPercentage = new PercentageByStudentPresent(StudentPresent.ABSENT, getPercentage(total, absents).intValue());
			presentismAnalysisData.setPercentagesByStudentPresent(Arrays.asList(presentsPercentage, latesPercentage, absentsPercentage));
			
			analysisData.add(presentismAnalysisData);
		}
		
		return analysisData;
	}

}
