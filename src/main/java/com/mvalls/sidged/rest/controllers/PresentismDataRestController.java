package com.mvalls.sidged.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.analytics.CoursePresentismData;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.services.PresentismDataService;

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
@RestController
@RequestMapping("/presentism-data")
public class PresentismDataRestController {
	
	private final PresentismDataService presentismDataService;
	
	@Autowired
	public PresentismDataRestController(PresentismDataService presentismDataService) {
		super();
		this.presentismDataService = presentismDataService;
	}

	@GetMapping("/course/{courseCode}")
	public CoursePresentismData getPresentismData(@PathVariable(name = "courseCode") String courseCode) {
		return this.presentismDataService.getPresentismByCourseCodeGroupedByMonth(courseCode);
	}

	@GetMapping("/student/{studentId}/year/{year}")
	public List<PresentismAnalysisData> getPresentismDataByStudentAndYear(@PathVariable(name = "studentId") Long studentId,
			@PathVariable(name = "year") int year) {
		return this.presentismDataService.getPresentismDataByStudentIdAndYear(studentId, year);
	}
	
}
