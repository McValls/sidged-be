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

import com.mvalls.sidged.annotations.JwtStudent;
import com.mvalls.sidged.annotations.JwtTeacher;
import com.mvalls.sidged.enums.UpdateAction;
import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.mappers.CourseDTOtoVOMapper;
import com.mvalls.sidged.mappers.CourseListMapper;
import com.mvalls.sidged.mappers.StudentAllMapper;
import com.mvalls.sidged.mappers.StudentModelMapper;
import com.mvalls.sidged.mappers.TeacherAllMapper;
import com.mvalls.sidged.mappers.TeacherModelMapper;
import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.rest.dtos.CourseDTO;
import com.mvalls.sidged.rest.dtos.CourseListDTO;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;
import com.mvalls.sidged.rest.dtos.StudentDTO;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;
import com.mvalls.sidged.services.CourseService;
import com.mvalls.sidged.valueObjects.CourseVO;

@RestController
@RequestMapping("/course")
public class CourseRestController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentAllMapper studentAllMapper;
	
	@Autowired
	private StudentModelMapper studentModelMapper;
	
	@Autowired
	private TeacherModelMapper teacherModelMapper;
	
	@Autowired
	private TeacherAllMapper teacherAllMapper;
	
	@Autowired
	private CourseListMapper courseListMapper;
	
	@Autowired
	private CourseDTOtoVOMapper courseDTOtoVOMapper;

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
	
	@PutMapping("/{id}/teacher")
	public Collection<TeacherAllDTO> updateTeachers(
			@PathVariable("id") Long id, 
			@RequestBody TeacherAllDTO dto,
			@RequestParam(name = "action", required = true) UpdateAction action){
		Collection<Teacher> teachers = courseService.updateTeacher(id, teacherModelMapper.map(dto), action);
		return teachers.stream()
				.map(teacher -> teacherAllMapper.map(teacher))
				.collect(Collectors.toList());
	}
	
	@PutMapping("/{id}/student")
	public Collection<StudentAllDTO> updateStudents(
			@PathVariable("id") Long id, 
			@RequestBody StudentDTO dto,
			@RequestParam(name = "action", required = true) UpdateAction action
			){
		Collection<Student> students = courseService.updateStudent(id, studentModelMapper.map(dto), action);
		return students.stream()
				.map(student -> studentAllMapper.map(student))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	public void createCourse(@RequestBody CourseDTO dto) {
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
	
}
