package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.enums.UpdateAction;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.services.StudentService;
import com.mvalls.sidged.core.services.UserStudentService;
import com.mvalls.sidged.mappers.StudentAllMapper;
import com.mvalls.sidged.mappers.StudentModelMapper;
import com.mvalls.sidged.mappers.UserStudentAllMapper;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;
import com.mvalls.sidged.rest.dtos.StudentDTO;

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
@RequestMapping("/student")
public class StudentRestController {

	private final StudentService studentService;
	private final UserStudentService userStudentService;
	private final StudentAllMapper studentAllMapper;
	private final UserStudentAllMapper userStudentAllMapper;
	private final StudentModelMapper studentModelMapper;
	
	@Autowired
	public StudentRestController(StudentService studentService, UserStudentService userStudentService) {
		super();
		this.studentService = studentService;
		this.userStudentService = userStudentService;
		this.studentAllMapper = new StudentAllMapper();
		this.userStudentAllMapper = new UserStudentAllMapper();
		this.studentModelMapper = new StudentModelMapper();
	}
	
	@GetMapping
	public Collection<StudentAllDTO> getAll() {
		List<Student> students = this.studentService.findAll();
		return students
			.stream()
			.map(this.studentAllMapper::map)
			.collect(Collectors.toList());
	}
	
	@GetMapping("/course/{courseCode}")
	public List<StudentAllDTO> getStudentsByCourseCode(@PathVariable("courseCode") String courseCode) {
		List<Student> students = this.studentService.findByCourseCode(courseCode);
		return students
				.stream()
				.map(this.studentAllMapper::map)
				.collect(Collectors.toList());
	}
	
	@JwtBackOffice
	@PutMapping("/course/{courseCode}")
	public List<StudentAllDTO> updateCourseStudent(HttpServletRequest request,
			@PathVariable("courseCode") String courseCode,
			@RequestBody StudentAllDTO dto,
			@RequestParam(name = "action", required = true) UpdateAction action) {
		List<Student> students = this.studentService.updateCourseStudent(courseCode, dto.getId(), action);
		return students
				.stream()
				.map(this.studentAllMapper::map)
				.collect(Collectors.toList());
	}
	
	@JwtBackOffice
	@PostMapping
	public void create(HttpServletRequest request, @RequestBody StudentDTO student) {
		studentService.create(studentModelMapper.map(student));
	}
	
	@JwtBackOffice
	@PutMapping("/{id}")
	public StudentAllDTO update(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody StudentDTO student) {
		Student modelStudent = studentModelMapper.map(student);
		if(id == null) {
			id = student.getId();
		}
		modelStudent.setId(id);
		
		modelStudent = studentService.update(modelStudent);
		return studentAllMapper.map(modelStudent);
	}
	
	@JwtBackOffice
	@DeleteMapping("/{id}")
	public void delete(HttpServletRequest request, @PathVariable("id") Long id) {
		studentService.delete(id);
	}
}
