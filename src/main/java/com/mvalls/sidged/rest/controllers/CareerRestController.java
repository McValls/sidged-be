package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.mappers.CareerMapper;
import com.mvalls.sidged.model.Career;
import com.mvalls.sidged.rest.dtos.CareerDTO;
import com.mvalls.sidged.services.CareerService;

@RestController
@RequestMapping("/career")
public class CareerRestController {
	
	@Autowired
	private CareerService careerService;
	
	@Autowired
	private CareerMapper careerMapper;

	@GetMapping
	public Collection<CareerDTO> getAllCareers() {
		Collection<Career> careers = careerService.findAll();
		
		return careers.stream()
				.map(career -> careerMapper.map(career))
				.collect(Collectors.toList());
	}
	
}
