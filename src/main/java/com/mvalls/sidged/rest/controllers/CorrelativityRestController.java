package com.mvalls.sidged.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.correlativity.Correlativity;
import com.mvalls.sidged.core.services.CorrelativityService;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.dtos.correlativity.CorrelativityDTO;

@RestController
@RequestMapping("/correlativity")
public class CorrelativityRestController {

	private final CorrelativityService correlativityService;

	@Autowired
	public CorrelativityRestController(CorrelativityService correlativityService) {
		super();
		this.correlativityService = correlativityService;
	}
	
	@GetMapping("/career/{careerCode}")
	public List<CorrelativityDTO> findAllByCareer(@PathVariable("careerCode") String careerCode) {
		List<Correlativity> correlativitiesByCareer = this.correlativityService.findAllByCareerCode(careerCode);
		return correlativitiesByCareer.stream()
			.map(CorrelativityDTO::from)
			.collect(Collectors.toList());
	}
	
	@JwtBackOffice
	@PutMapping("/subject/{subjectCode}/add/{subjectCodeToAdd}")
	public CorrelativityDTO addCorrelativity(HttpServletRequest request,
			@PathVariable("subjectCode") String subjectCode,
			@PathVariable("subjectCodeToAdd") String subjectCodeToAdd) {
		Correlativity updated = 
				this.correlativityService.addCorrelativity(subjectCode, subjectCodeToAdd);
		
		return CorrelativityDTO.from(updated);
	}
	
	@JwtBackOffice
	@DeleteMapping("/subject/{subjectCode}/delete/{subjectCodeToDelete}")
	public CorrelativityDTO deleteCorrelativity(HttpServletRequest request,
			@PathVariable("subjectCode") String subjectCode,
			@PathVariable("subjectCodeToDelete") String subjectCodeToDelete) {
		Correlativity updated = 
				this.correlativityService.deleteCorrelativity(subjectCode, subjectCodeToDelete);
		
		return CorrelativityDTO.from(updated);
	}
	
}
