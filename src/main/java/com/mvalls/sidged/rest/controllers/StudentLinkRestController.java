package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.services.StudentLinkService;
import com.mvalls.sidged.mappers.StudentLinkMapper;
import com.mvalls.sidged.core.model.StudentLink;
import com.mvalls.sidged.rest.dtos.StudentLinkDTO;


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
