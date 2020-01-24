package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.Career;
import com.mvalls.sidged.repositories.CareerRepository;

@Service
public class CareerService extends GenericService<Career, CareerRepository>{

	@Autowired
	private CareerRepository careerRepository;
	
	@Override
	protected CareerRepository getRepository() {
		return careerRepository;
	}
	
}
