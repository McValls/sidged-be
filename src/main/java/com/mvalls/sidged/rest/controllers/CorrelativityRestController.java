package com.mvalls.sidged.rest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.correlativity.Correlativity;
import com.mvalls.sidged.core.services.CorrelativityService;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.dtos.correlativity.CorrelativityAction;
import com.mvalls.sidged.rest.dtos.correlativity.CorrelativityDTO;
import com.mvalls.sidged.rest.dtos.correlativity.CorrelativityUpdateRequestDTO;

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
	
	@GetMapping("/subject/{subjectCode}")
	public CorrelativityDTO findCorrelativityBySubject(@PathVariable("subjectCode") String subjectCode) {
		Optional<Correlativity> correlativity = this.correlativityService.findBySubjectCode(subjectCode);
		
		return correlativity.map(CorrelativityDTO::from).orElse(null);
	}
	
	@JwtBackOffice
	@PutMapping("/subject/{subjectCode}/updateList")
	public List<CorrelativityDTO> updateCorrelativities(HttpServletRequest request,
			@PathVariable("subjectCode") String subjectCode,
			@RequestBody CorrelativityUpdateRequestDTO body) {
		return body.getUpdates()
			.stream()
			.map(update -> update.getAction() == CorrelativityAction.ADD ?
					this.correlativityService.addCorrelativity(subjectCode, update.getSubjectCode()) :
					this.correlativityService.deleteCorrelativity(subjectCode, update.getSubjectCode()))
			.map(CorrelativityDTO::from)
			.collect(Collectors.toList());
	}
	
}
