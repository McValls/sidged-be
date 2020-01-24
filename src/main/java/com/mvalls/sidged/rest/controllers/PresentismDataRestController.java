package com.mvalls.sidged.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.model.analytics.CoursePresentismData;
import com.mvalls.sidged.services.CourseService;

@RestController
@RequestMapping("/presentism-data")
public class PresentismDataRestController {
	
	@Autowired private CourseService courseService;
	
	@GetMapping("/course/{courseId}")
	public CoursePresentismData getPresentismData(@PathVariable(name = "courseId") Long courseId) {
		return courseService.getPresentismByCourseGroupedByMonth(courseId);
	}

}
