package com.mvalls.sidged.core.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.analytics.CoursePresentismData;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.repositories.CourseClassRepository;

public class PresentismDataService {
	
	private final CourseClassRepository courseClassRepository;
	private final PresentAnalysisCalculator presentAnalysisCalculator;
	
	public PresentismDataService(CourseClassRepository courseClassRepository,
			PresentAnalysisCalculator presentAnalysisCalculator) {
		super();
		this.courseClassRepository = courseClassRepository;
		this.presentAnalysisCalculator = presentAnalysisCalculator;
	}


	public CoursePresentismData getPresentismByCourseCodeGroupedByMonth(String courseCode) {
		List<CourseClass> classes = this.courseClassRepository.findByCourseCode(courseCode);
		return this.presentAnalysisCalculator.getPresentismByCourseGroupedByMonth(classes);
	}
	
	public List<PresentismAnalysisData> getPresentismDataByStudentIdAndYear(Long studentId, int year) {
//		List<CourseClass> courseClasses = this.courseClassRepository.findAllByStudentId(studentId)
//				.stream()
//				.filter(courseClass -> courseClass.getCourse().getYear().intValue() == year)
//				.collect(Collectors.toList());
//
//		Map<Course, List<ClassStudentPresent>> presentismGroupedByCourse = new HashMap<>();
//		
//		for (CourseClass courseClass : courseClasses) {
//			Course course = courseClass.getCourse();
//			if (!presentismGroupedByCourse.containsKey(course)) {
//				presentismGroupedByCourse.put(course, new ArrayList<>());
//			}
//			
//			presentismGroupedByCourse.get(course)
//				.addAll(courseClass.getStudentPresents()
//						.stream()
//						.filter(studentPresent -> studentPresent.getStudent().getId().equals(studentId))
//						.collect(Collectors.toList()));
//		}
		
		Map<Course, List<ClassStudentPresent>> presentismGroupedByCourse = this.courseClassRepository.findAllByStudentId(studentId)
				.stream()
				.filter(courseClass -> courseClass.getCourse().getYear().intValue() == year)
				.collect(Collectors.toMap(
						courseClass -> courseClass.getCourse(), 
						courseClass -> courseClass.getStudentPresents()
							.stream()
							.filter(studentPresent -> studentPresent.getStudent().getId().equals(studentId))
							.collect(Collectors.toList()),
						(sp1, sp2) -> Stream.of(sp1, sp2).flatMap(Collection::stream).collect(Collectors.toList())
						));
		
		return this.presentAnalysisCalculator.getPresentismByStudentGroupedByCourse(presentismGroupedByCourse);
		
	}
}
