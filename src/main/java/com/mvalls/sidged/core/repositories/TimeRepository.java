package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Time;

public interface TimeRepository {

	Time findById(Long id);
	List<Time> findAll();
	
}
