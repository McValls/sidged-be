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
import com.mvalls.sidged.mappers.CourseDTOtoVOMapper;
import com.mvalls.sidged.mappers.CourseListMapper;
import com.mvalls.sidged.mappers.StudentAllMapper;
import com.mvalls.sidged.mappers.StudentModelMapper;
import com.mvalls.sidged.mappers.TeacherAllMapper;
import com.mvalls.sidged.mappers.TeacherModelMapper;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.annotations.JwtStudent;
import com.mvalls.sidged.rest.annotations.JwtTeacher;
import com.mvalls.sidged.rest.dtos.CourseDTO;
import com.mvalls.sidged.rest.dtos.CourseListDTO;
import com.mvalls.sidged.rest.dtos.NotifyStudentsDTO;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.valueObjects.CourseVO;

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
	private final StudentAllMapper studentAllMapper;
	private final StudentModelMapper studentModelMapper;
	private final TeacherModelMapper teacherModelMapper;
	private final TeacherAllMapper teacherAllMapper;
	private final CourseListMapper courseListMapper;
	private final CourseDTOtoVOMapper courseDTOtoVOMapper;
	
	@Autowired
	public CourseRestController(CourseService courseService) {
		super();
		this.courseService = courseService;
		this.studentAllMapper = new StudentAllMapper();
		this.studentModelMapper = new StudentModelMapper();
		this.teacherModelMapper = new TeacherModelMapper();
		this.teacherAllMapper = new TeacherAllMapper();
		this.courseListMapper = new CourseListMapper();
		this.courseDTOtoVOMapper = new CourseDTOtoVOMapper();
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
		CourseVO courseValueObject = courseDTOtoVOMapper.map(dto);
		courseService.createCourse(courseValueObject);
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
		courseService.sendEmailToStudents(notifyStudentsDTO.getCourseCode(), 
				userTeacher.getTeacher(), 
				notifyStudentsDTO.getSubject(), 
				notifyStudentsDTO.getMessage());
	}
	
}
