package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mvalls.sidged.core.model.correlativity.Correlativity;
import com.mvalls.sidged.core.repositories.CorrelativityRepository;
import com.mvalls.sidged.core.repositories.SubjectRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CorrelativityService {

	private final CorrelativityRepository correlativityRepository;
	private final SubjectRepository subjectRepository;
	
	public CorrelativityService(CorrelativityRepository correlativityRepository, SubjectRepository subjectRepository) {
		super();
		this.correlativityRepository = correlativityRepository;
		this.subjectRepository = subjectRepository;
	}

	public List<Correlativity> findAllByCareerCode(String careerCode) {
		return this.correlativityRepository.findAllByCareerCode(careerCode);
	}

	public Correlativity addCorrelativity(String subjectCode, String newCorrelativeSubjectCode) {
		var subject = subjectRepository.findByCode(subjectCode);
		if (subject == null) throw new IllegalArgumentException("Subject code " + subjectCode + " is not a valid subject.");
		
		var newCorrelativeSubject = subjectRepository.findByCode(newCorrelativeSubjectCode);
		if (newCorrelativeSubject == null) throw new IllegalArgumentException("Subject code " + newCorrelativeSubject + " is not a valid subject.");
		
		var correlativity = 
				this.correlativityRepository.findBySubjectCode(subjectCode)
				.orElseGet(() -> new Correlativity(subject, List.of()));
		
		var newCorrelative = 
				this.correlativityRepository.findBySubjectCode(newCorrelativeSubjectCode)
				.orElseGet(() -> new Correlativity(newCorrelativeSubject, List.of()));
		
		var mergedCorrelativities = 
				new Correlativity(correlativity.getSubject(), 
						Stream.of(correlativity.getCorrelativities(), List.of(newCorrelative))
						.flatMap(Collection::stream)
						.collect(Collectors.toList()));
		
		log.info("Could create a new Correlativity: {}", mergedCorrelativities);
		
		return this.correlativityRepository.addCorrelativity(subject, newCorrelativeSubject);
	}

	public Correlativity deleteCorrelativity(String subjectCode, String subjectToRemoveCode) {
		var subject = subjectRepository.findByCode(subjectCode);
		var subjectToRemove = subjectRepository.findByCode(subjectToRemoveCode);
		
		return this.correlativityRepository.deleteCorrelativity(subject, subjectToRemove);
	}

}
