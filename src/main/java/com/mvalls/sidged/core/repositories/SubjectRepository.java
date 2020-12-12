package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Subject;

public interface SubjectRepository {

	List<Subject> findAll();
	List<Subject> findByCareerCode(String careerCode);
	Subject findByCode(String code);
	Subject create(Subject subject);
	Subject update(Subject subject);
	boolean delete(String code);
	
}
