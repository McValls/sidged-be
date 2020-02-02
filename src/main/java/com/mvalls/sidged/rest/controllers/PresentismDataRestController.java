package com.mvalls.sidged.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.model.analytics.CoursePresentismData;
import com.mvalls.sidged.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.services.ClassStudentPresentService;
import com.mvalls.sidged.services.CourseService;

@RestController
@RequestMapping("/presentism-data")
public class PresentismDataRestController {
	
	private final ClassStudentPresentService classStudentPresentService;
	private final CourseService courseService;
	
	@Autowired
	public PresentismDataRestController(ClassStudentPresentService classStudentPresentService,
			CourseService courseService) {
		super();
		this.classStudentPresentService = classStudentPresentService;
		this.courseService = courseService;
	}

	@GetMapping("/course/{courseId}")
	public CoursePresentismData getPresentismData(@PathVariable(name = "courseId") Long courseId) {
		return courseService.getPresentismByCourseGroupedByMonth(courseId);
	}

	@GetMapping("/student/{studentId}/year/{year}")
	public List<PresentismAnalysisData> getPresentismDataByStudentAndYear(@PathVariable(name = "studentId") Long studentId,
			@PathVariable(name = "year") int year) {
		return classStudentPresentService.getPresentismDataByStudentIdAndYear(studentId, year);
	}
	
}
