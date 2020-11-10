package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.enums.UpdateAction;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.Teacher;
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
import com.mvalls.sidged.rest.dtos.StudentAllDTO;
import com.mvalls.sidged.rest.dtos.StudentDTO;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;
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
	
	@GetMapping("/{id}/teacher")
	public Collection<TeacherAllDTO> getTeachersByCourse(@PathVariable("id") Long id) {
		Collection<Teacher> teachers = courseService.findTeachersByCourse(id);
		return teachers.stream()
				.map(teacher -> teacherAllMapper.map(teacher))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}/student")
	public Collection<StudentAllDTO> getStudentsByCourse(@PathVariable(value = "id") Long id) {
		Collection<Student> students = courseService.findStudentsByCourse(id);
		
		Collection<StudentAllDTO> studentsList = students.stream()
				.map(student -> studentAllMapper.map(student))
				.collect(Collectors.toList());
		
		return studentsList;
	}
	
	@JwtBackOffice
	@PutMapping("/{id}/teacher")
	public Collection<TeacherAllDTO> updateTeachers(
			HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestBody TeacherAllDTO dto,
			@RequestParam(name = "action", required = true) UpdateAction action) {
		Collection<Teacher> teachers = courseService.updateTeacher(id, teacherModelMapper.map(dto), action);
		return teachers.stream()
				.map(teacher -> teacherAllMapper.map(teacher))
				.collect(Collectors.toList());
	}
	
	@JwtBackOffice
	@PutMapping("/{id}/student")
	public Collection<StudentAllDTO> updateStudents(
			HttpServletRequest request,
			@PathVariable("id") Long id, 
			@RequestBody StudentDTO dto,
			@RequestParam(name = "action", required = true) UpdateAction action) {
		Collection<Student> students = courseService.updateStudent(id, studentModelMapper.map(dto), action);
		return students.stream()
				.map(student -> studentAllMapper.map(student))
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
			@RequestBody NotifyStudentsDTO notifyStudentsDTO) {
		courseService.sendEmailToStudents(notifyStudentsDTO.getCourseId(), 
				userTeacher.getTeacher().getId(), 
				notifyStudentsDTO.getSubject(), 
				notifyStudentsDTO.getMessage());
	}
	
}
