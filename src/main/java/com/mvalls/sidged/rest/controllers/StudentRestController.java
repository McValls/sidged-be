package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.mappers.StudentAllMapper;
import com.mvalls.sidged.mappers.StudentModelMapper;
import com.mvalls.sidged.mappers.UserStudentAllMapper;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;
import com.mvalls.sidged.rest.dtos.StudentDTO;
import com.mvalls.sidged.services.StudentService;
import com.mvalls.sidged.services.UserStudentService;

@RestController
@RequestMapping("/student")
public class StudentRestController {

	private final StudentService studentService;
	private final UserStudentService userStudentService;
	private final StudentAllMapper studentAllMapper;
	private final UserStudentAllMapper userStudentAllMapper;
	private final StudentModelMapper studentModelMapper;
	
	@Autowired
	public StudentRestController(StudentService studentService, UserStudentService userStudentService,
			StudentAllMapper studentAllMapper, UserStudentAllMapper userStudentAllMapper,
			StudentModelMapper studentModelMapper) {
		super();
		this.studentService = studentService;
		this.userStudentService = userStudentService;
		this.studentAllMapper = studentAllMapper;
		this.userStudentAllMapper = userStudentAllMapper;
		this.studentModelMapper = studentModelMapper;
	}
	
	@GetMapping
	public Collection<StudentAllDTO> getAll() {
		Collection<UserStudent> studentsDb = userStudentService.findAll();
		
		return studentsDb.stream()
				.map(student -> userStudentAllMapper.map(student))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	public void create(@RequestBody StudentDTO student) {
		studentService.create(studentModelMapper.map(student));
	}
	
	@PutMapping("/{id}")
	public StudentAllDTO update(@PathVariable("id") Long id, @RequestBody StudentDTO student) {
		Student modelStudent = studentModelMapper.map(student);
		if(id == null) {
			id = student.getId();
		}
		modelStudent.setId(id);
		
		modelStudent = studentService.update(modelStudent);
		return studentAllMapper.map(modelStudent);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		Student student = Student.builder()
				.id(id)
				.build();
		studentService.delete(student);
	}
}
