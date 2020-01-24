package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.Period;
import com.mvalls.sidged.model.PeriodType;
import com.mvalls.sidged.repositories.PeriodRepository;

@Service
public class PeriodService extends GenericService<Period, PeriodRepository>{

	@Autowired
	private PeriodRepository periodRepository;
	
	public Period findByTypeAndNumber(PeriodType type, Integer number) {
		return periodRepository.findByPeriodTypeAndNumber(type, number);
	}
	
	@Override
	protected PeriodRepository getRepository() {
		return periodRepository;
	}
	
}
