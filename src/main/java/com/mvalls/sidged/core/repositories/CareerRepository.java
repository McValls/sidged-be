package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Career;

public interface CareerRepository {

	Career findByCode(String careerCode);
	List<Career> findAll();
	
}
