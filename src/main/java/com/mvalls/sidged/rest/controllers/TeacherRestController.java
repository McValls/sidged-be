package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.mappers.TeacherAllMapper;
import com.mvalls.sidged.mappers.TeacherModelMapper;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;
import com.mvalls.sidged.services.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherRestController {

	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private TeacherAllMapper teacherAllMapper;
	
	@Autowired
	private TeacherModelMapper teacherModelMapper;
	
	@GetMapping
	public Collection<TeacherAllDTO> getAll() {
		Collection<Teacher> teachersDb = teacherService.findAll();
		
		Collection<TeacherAllDTO> teachersResponse = teachersDb.stream()
				.map(teacher -> teacherAllMapper.map(teacher))
				.collect(Collectors.toList());
		
		return teachersResponse;
	}
	
	@PutMapping("/{id}")
	public TeacherAllDTO update(@PathVariable(value = "id") Long id, @RequestBody TeacherAllDTO dto) {
		Teacher teacher = teacherModelMapper.map(dto);
		if(id == null) {
			id = dto.getId();
		}
		teacher.setId(id);
		teacher = teacherService.update(teacher);

		return teacherAllMapper.map(teacher);
	}
}
