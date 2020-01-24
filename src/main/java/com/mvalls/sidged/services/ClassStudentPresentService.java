package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.model.ClassStudentPresent;
import com.mvalls.sidged.model.StudentPresent;
import com.mvalls.sidged.repositories.ClassStudentPresentRepository;

@Service
public class ClassStudentPresentService extends GenericService<ClassStudentPresent, ClassStudentPresentRepository>{

	@Autowired private ClassStudentPresentRepository classStudentPresentRepository;
	
	@Transactional
	public void updatePresent(Long courseClassId, Long studentId, StudentPresent present) {
		ClassStudentPresent classStudentPresent = classStudentPresentRepository.findByCourseClassIdAndStudentId(courseClassId, studentId);
		classStudentPresent.setPresent(present);
		classStudentPresentRepository.save(classStudentPresent);
	}
	
	@Override
	protected ClassStudentPresentRepository getRepository() {
		return classStudentPresentRepository;
	}
}
