package com.mvalls.sidged.core.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.SubjectRepository;

public class SubjectService {
	
	private final SubjectRepository subjectRepository;
	private final CareerRepository careerRepository;

	public SubjectService(SubjectRepository subjectRepository,
			CareerRepository careerRepository) {
		super();
		this.subjectRepository = subjectRepository;
		this.careerRepository = careerRepository;
	}

	public List<Subject> findAll() {
		return this.subjectRepository.findAll();
	}

	public Subject findByCode(String code) {
		if (code == null) throw new IllegalArgumentException("Code must not be null");
		return this.subjectRepository.findByCode(code);
	}

	public Subject create(String name, String code, String careerCode) {
		if (StringUtils.isEmpty(code) ||
				StringUtils.isEmpty(name) || 
				StringUtils.isEmpty(careerCode)) {
			throw new IllegalArgumentException("Code, name and careerCode must be not null");
		}
		
		Career career = this.careerRepository.findByCode(careerCode);
		if (career == null) {
			throw new IllegalArgumentException("Career with code " + careerCode + " does not exists.");
		}
		
		return this.subjectRepository.create(new Subject(name, code, career));
	}

	public Subject update(String code, String newName) {
		Subject original = this.findByCode(code);
		Subject updateCopy = new Subject(original.getId(), 
				newName, original.getCode(), original.getCareer());
		return this.subjectRepository.update(updateCopy);
	}

	public boolean delete(String code) {
		if (StringUtils.isEmpty(code)) throw new IllegalArgumentException("Code must be non-null nor empty");
		return this.subjectRepository.delete(code);
	}

	public List<Subject> findByCareerCode(String careerCode) {
		return this.subjectRepository.findByCareerCode(careerCode);
	}


}
