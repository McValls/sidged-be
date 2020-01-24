package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Career;
import com.mvalls.sidged.rest.dtos.CareerDTO;

@Component
public class CareerMapper extends GenericMapper<Career, CareerDTO>{

	@Override
	public CareerDTO map(Career career) {
		CareerDTO dto = CareerDTO.builder()
				.id(career.getId())
				.name(career.getName())
				.build();
		return dto;
	}
	
}
