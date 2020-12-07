package com.mvalls.sidged.database.dtos;

import java.util.List;

import lombok.Getter;

@Getter
public class SubjectDependenciesDTO {

	private final SubjectDTO subject;
	private final List<SubjectDTO> dependencies;

	public SubjectDependenciesDTO(SubjectDTO subject, List<SubjectDTO> dependencies) {
		this.subject = subject;
		this.dependencies = dependencies;
	}

}
