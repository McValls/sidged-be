package com.mvalls.sidged.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.services.SubjectService;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.dtos.subject.SubjectCreateDTO;
import com.mvalls.sidged.rest.dtos.subject.SubjectFindAllResponseDTO;
import com.mvalls.sidged.rest.dtos.subject.SubjectUpdateDTO;

@RestController
@RequestMapping("/subject")
public class SubjectRestController {
	
	private final SubjectService subjectService;
	
	public SubjectRestController(SubjectService subjectService) {
		super();
		this.subjectService = subjectService;
	}

	@GetMapping
	public List<SubjectFindAllResponseDTO> getAll() {
		return this.subjectService.findAll()
				.stream()
				.map(SubjectFindAllResponseDTO::build)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/career/{careerCode}")
	public List<SubjectFindAllResponseDTO> getAllByCareer(@PathVariable("careerCode") String careerCode) {
		return this.subjectService.findByCareerCode(careerCode)
				.stream()
				.map(SubjectFindAllResponseDTO::build)
				.collect(Collectors.toList());
	}
	
	@JwtBackOffice
	@PostMapping
	public void create(HttpServletRequest request,
			@RequestBody SubjectCreateDTO dto) {
		this.subjectService.create(dto.getName(), dto.getCode(), dto.getCareerCode());
	}
	
	@JwtBackOffice
	@PutMapping("/{subjectCode}")
	public void update(HttpServletRequest request,
			@PathVariable("subjectCode") String subjectCode,
			@RequestBody SubjectUpdateDTO dto) {
		this.subjectService.update(subjectCode, dto.getName());
	}
	
	@JwtBackOffice
	@DeleteMapping("/{subjectCode}")
	public void delete(HttpServletRequest request,
			@PathVariable("subjectCode") String subjectCode) {
		this.subjectService.delete(subjectCode);
	}
	
}
