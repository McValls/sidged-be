package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.mappers.StudentLinkMapper;
import com.mvalls.sidged.model.StudentLink;
import com.mvalls.sidged.rest.dtos.StudentLinkDTO;
import com.mvalls.sidged.services.StudentLinkService;

@RestController
@RequestMapping("/student-link")
public class StudentLinkRestController {
	
	private final StudentLinkService studentLinkService;
	private final StudentLinkMapper studentLinkMapper;
	
	@Autowired
	public StudentLinkRestController(StudentLinkService studentLinkService, StudentLinkMapper studentLinkMapper) {
		this.studentLinkService = studentLinkService;
		this.studentLinkMapper = studentLinkMapper;
	}
	
	@GetMapping("/")
	public List<StudentLinkDTO> getLinks() {
		Collection<StudentLink> studentLinks = this.studentLinkService.findAll();
		return studentLinks.stream()
			.map(studentLink -> studentLinkMapper.map(studentLink))
			.collect(Collectors.toList());
	}

}
