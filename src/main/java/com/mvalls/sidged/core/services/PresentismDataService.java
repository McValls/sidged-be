package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.analytics.CoursePresentismData;
import com.mvalls.sidged.core.model.analytics.PresentPercentages;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;

public class PresentismDataService {
	
	private final CourseRepository courseRepository;
	private final CourseClassRepository courseClassRepository;
	private final PresentAnalysisCalculator presentAnalysisCalculator;
	
	public PresentismDataService(CourseRepository courseRepository,
			CourseClassRepository courseClassRepository,
			PresentAnalysisCalculator presentAnalysisCalculator) {
		super();
		this.courseRepository = courseRepository;
		this.courseClassRepository = courseClassRepository;
		this.presentAnalysisCalculator = presentAnalysisCalculator;
	}


	public CoursePresentismData getPresentismByCourseCodeGroupedByMonth(String courseCode) {
		List<CourseClass> classes = this.courseClassRepository.findByCourseCode(courseCode);
		return this.presentAnalysisCalculator.getPresentismByCourseGroupedByMonth(classes);
	}
	
	public List<PresentismAnalysisData> getPresentismDataByStudentIdAndYear(Long studentId, int year) {
		Function<CourseClass, Course> keyMapper = cc -> cc.getCourse();
		
		Function<CourseClass, List<ClassStudentPresent>> valueMapper = cc -> cc.getStudentPresents()
				.stream()
				.filter(s -> this.sameStudent(s, studentId))
				.collect(Collectors.toList());
				
		BinaryOperator<List<ClassStudentPresent>> mergeFunction = (sp1, sp2) -> Stream.of(sp1, sp2)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		Map<Course, List<ClassStudentPresent>> presentismGroupedByCourse = this.courseClassRepository.findAllByStudentId(studentId)
				.stream()
				.filter(courseClass -> courseClass.getCourse().getYear().intValue() == year)
				.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
		
		return this.presentAnalysisCalculator.getPresentismByStudentGroupedByCourse(presentismGroupedByCourse);
	}
	
	private boolean sameStudent(ClassStudentPresent studentPresent, Long id) {
		return studentPresent.getStudent().getId().equals(id);
	}
	
	public PresentismAnalysisData getPresentismAnalysis(String courseCode) {
		Course course = this.courseRepository.findByCode(courseCode).orElseThrow();
		List<PresentPercentages> presentPercentagesByClasses = presentAnalysisCalculator
				.getPresentPercentagesByClasses(course.getClasses());

		PresentismAnalysisData analysisData = PresentismAnalysisData.builder()
				.percentagesByClassNumber(presentPercentagesByClasses)
				.courseCode(course.getCode())
				.courseName(course.getName())
				.build();
		return analysisData;
	}
}
