package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.services.CareerService;
import com.mvalls.sidged.mappers.CareerMapper;
import com.mvalls.sidged.rest.dtos.CareerDTO;

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
