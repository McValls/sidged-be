package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Career;

public interface CareerRepository {

	Career findByCode(String careerCode);
	Career findById(Long id);
	List<Career> findAll();
	Career create(Career career);
	Career update(Career career);
	boolean delete(Career career);
	
}
