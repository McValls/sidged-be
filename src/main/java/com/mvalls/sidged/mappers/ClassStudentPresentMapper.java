package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.ClassStudentPresent;
import com.mvalls.sidged.rest.dtos.ClassStudentDTO;

@Component
public class ClassStudentPresentMapper extends GenericMapper<ClassStudentPresent, ClassStudentDTO> {

	@Override
	public ClassStudentDTO map(ClassStudentPresent studentPresent) {
		return ClassStudentDTO.builder()
				.id(studentPresent.getStudent().getId())
				.names(studentPresent.getStudent().getNames())
				.lastname(studentPresent.getStudent().getLastname())
				.present(studentPresent.getPresent())
				.build();
	}
	
}
