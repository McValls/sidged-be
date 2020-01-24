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

import com.mvalls.sidged.mappers.StudentAllMapper;
import com.mvalls.sidged.mappers.StudentModelMapper;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;
import com.mvalls.sidged.rest.dtos.StudentDTO;
import com.mvalls.sidged.services.StudentService;

@RestController
@RequestMapping("/student")
public class StudentRestController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentAllMapper studentAllMapper;
	
	@Autowired
	private StudentModelMapper studentModelMapper;
	
	@GetMapping
	public Collection<StudentAllDTO> getAll() {
		Collection<Student> studentsDb = studentService.findAll();
		
		return studentsDb.stream()
				.map(student -> studentAllMapper.map(student))
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
