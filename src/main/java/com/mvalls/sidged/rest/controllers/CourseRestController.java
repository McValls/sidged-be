package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.services.CourseService;
import com.mvalls.sidged.core.services.NotificationService;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.annotations.JwtStudent;
import com.mvalls.sidged.rest.annotations.JwtTeacher;
import com.mvalls.sidged.rest.dtos.CourseDTO;
import com.mvalls.sidged.rest.dtos.CourseListDTO;
import com.mvalls.sidged.rest.dtos.NotifyStudentsDTO;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.rest.mappers.CourseListMapper;

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
@RequestMapping("/course")
public class CourseRestController {
	
	private final CourseService courseService;
	private final NotificationService notificationService;
	private final CourseListMapper courseListMapper;
	
	@Autowired
	public CourseRestController(CourseService courseService, NotificationService notificationService) {
		super();
		this.courseService = courseService;
		this.notificationService = notificationService;
		this.courseListMapper = new CourseListMapper();
	}

	@GetMapping
	public Collection<CourseListDTO> getAllCourses() {
		Collection<Course> coursesDB = courseService.findAll();
		return coursesDB.stream()
				.map(course -> courseListMapper.map(course))
				.collect(Collectors.toList());
	}

	@JwtBackOffice
	@PostMapping
	public void createCourse(HttpServletRequest request, @RequestBody CourseDTO dto) {
		Course.CourseBuilder courseBuilder = Course
				.builder()
				.name(dto.getName())
				.code(dto.getCourseCode())
				.shift(dto.getShift())
				.year(dto.getYear());
				
		courseService.createCourse(courseBuilder, 
				dto.getPeriodNumber(), 
				dto.getPeriodType(),
				dto.getTimeSinceId(),
				dto.getTimeUntilId(),
				dto.getCareerCode());
	}
	
	@JwtTeacher
	@GetMapping("/by-teacher")
	public Collection<CourseListDTO> getCoursesByTeacher(HttpServletRequest request, UserTeacher userTeacher) {
		Collection<Course> coursesByTeacher = courseService.findByTeacher(userTeacher.getTeacher().getId());
		return coursesByTeacher.stream()
				.map(course -> courseListMapper.map(course))
				.collect(Collectors.toList());
	}
	
	@JwtStudent
	@GetMapping("/by-student")
	public Collection<CourseListDTO> getCoursesByStudent(HttpServletRequest request, UserStudent userStudent) {
		Collection<Course> coursesByStudent = courseService.findByStudent(userStudent.getStudent().getId());
		return coursesByStudent.stream()
				.map(course -> courseListMapper.map(course))
				.collect(Collectors.toList());
	}
	
	
	@JwtTeacher
	@PostMapping("/notify-students")
	public void notifyCourseStudents(HttpServletRequest request, 
			UserTeacher userTeacher,
			@RequestBody NotifyStudentsDTO notifyStudentsDTO) throws UnauthorizedUserException {
		this.notificationService.sendEmailToStudents(notifyStudentsDTO.getCourseCode(), 
				userTeacher.getTeacher(), 
				notifyStudentsDTO.getSubject(), 
				notifyStudentsDTO.getMessage());
	}
	
}
