package com.mvalls.sidged.rest.dtos.correlativity;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.model.correlativity.Correlativity;
import com.mvalls.sidged.rest.dtos.DataTransferObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CorrelativityDTO extends DataTransferObject {
	
	private final SubjectDTO subject;
	private final List<SubjectDTO> dependencies;
	private final int level;
	
	public static CorrelativityDTO from(Correlativity correlativity) {
		return new CorrelativityDTO(mapSubject(correlativity.getSubject()), 
				mapDependencies(correlativity.getDependenciesTree()), 
				correlativity.getLevel());
	}
	
	private static SubjectDTO mapSubject(Subject subject) {
		return new SubjectDTO(subject.getCode(), subject.getName());
	}
	
	private static List<SubjectDTO> mapDependencies(List<Subject> dependencies) {
		return dependencies.stream()
				.map(CorrelativityDTO::mapSubject)
				.collect(Collectors.toList());
	}

}
