package com.mvalls.sidged.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.model.ClassStudentPresent;
import com.mvalls.sidged.model.StudentPresent;
import com.mvalls.sidged.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.repositories.ClassStudentPresentRepository;

@Service
public class ClassStudentPresentService extends GenericService<ClassStudentPresent, ClassStudentPresentRepository>{

	private final PresentAnalysisCalculator presentAnalysisCalculator;
	private final ClassStudentPresentRepository classStudentPresentRepository;
	
	@Autowired
	public ClassStudentPresentService(PresentAnalysisCalculator presentAnalysisCalculator,
			ClassStudentPresentRepository classStudentPresentRepository) {
		super();
		this.presentAnalysisCalculator = presentAnalysisCalculator;
		this.classStudentPresentRepository = classStudentPresentRepository;
	}

	@Transactional
	public void updatePresent(Long courseClassId, Long studentId, StudentPresent present) {
		ClassStudentPresent classStudentPresent = classStudentPresentRepository.findByCourseClassIdAndStudentId(courseClassId, studentId);
		classStudentPresent.setPresent(present);
		classStudentPresentRepository.save(classStudentPresent);
	}
	
	public List<PresentismAnalysisData> getPresentismDataByStudentIdAndYear(Long studentId, int year) {
		List<ClassStudentPresent> classStudentPresents = classStudentPresentRepository.findByStudentId(studentId).stream()
				.filter(classStudentPresent -> classStudentPresent.getCourseClass().getDate().getYear() == year)
				.collect(Collectors.toList());
		return presentAnalysisCalculator.getPresentismByStudentGroupedByCourse(classStudentPresents, year);
	}
	
	@Override
	protected ClassStudentPresentRepository getRepository() {
		return classStudentPresentRepository;
	}
}
