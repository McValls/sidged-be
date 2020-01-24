package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.mappers.TimeMapper;
import com.mvalls.sidged.model.Time;
import com.mvalls.sidged.rest.dtos.TimeDTO;
import com.mvalls.sidged.services.TimeService;

@RestController
@RequestMapping("/time")
public class TimeRestController {
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private TimeMapper timeMapper;

	@GetMapping
	public Collection<TimeDTO> getAll() {
		Collection<Time> times = timeService.findAll();
		Collection<TimeDTO> timesDto = times
				.stream()
				.map(time -> timeMapper.map(time))
				.collect(Collectors.toList());
		
		return timesDto;
	}
	
}
