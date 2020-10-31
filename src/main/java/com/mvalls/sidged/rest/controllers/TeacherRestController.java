package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.annotations.JwtBackOffice;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.services.TeacherService;
import com.mvalls.sidged.core.services.UserTeacherService;
import com.mvalls.sidged.mappers.TeacherAllMapper;
import com.mvalls.sidged.mappers.TeacherModelMapper;
import com.mvalls.sidged.mappers.UserTeacherAllMapper;
import com.mvalls.sidged.rest.dtos.TeacherAllDTO;

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
@RequestMapping("/teacher")
public class TeacherRestController {

	private final TeacherService teacherService;
	private final UserTeacherAllMapper userTeacherAllMapper;
	private final TeacherAllMapper teacherAllMapper;
	private final TeacherModelMapper teacherModelMapper;
	private final UserTeacherService userTeacherService;
	
	@Autowired
	public TeacherRestController(TeacherService teacherService, UserTeacherAllMapper userTeacherAllMapper,
			TeacherAllMapper teacherAllMapper, TeacherModelMapper teacherModelMapper, 
			UserTeacherService userTeacherService) {
		super();
		this.teacherService = teacherService;
		this.userTeacherAllMapper = userTeacherAllMapper;
		this.teacherAllMapper = teacherAllMapper;
		this.teacherModelMapper = teacherModelMapper;
		this.userTeacherService = userTeacherService;
	}

	
	@GetMapping
	public Collection<TeacherAllDTO> getAll() {
		Collection<UserTeacher> teachersDb = userTeacherService.findAll();
		
		Collection<TeacherAllDTO> teachersResponse = teachersDb.stream()
				.map(teacher -> userTeacherAllMapper.map(teacher))
				.collect(Collectors.toList());
		
		return teachersResponse;
	}
	
	@JwtBackOffice
	@PutMapping("/{id}")
	public TeacherAllDTO update(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody TeacherAllDTO dto) {
		Teacher teacher = teacherModelMapper.map(dto);
		if(id == null) {
			id = dto.getId();
		}
		teacher.setId(id);
		teacher = teacherService.update(teacher);

		return teacherAllMapper.map(teacher);
	}
}
