package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.Time;
import com.mvalls.sidged.repositories.TimeRepository;

@Service
public class TimeService extends GenericService<Time, TimeRepository>{
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Override
	protected TimeRepository getRepository() {
		return timeRepository;
	}

}
