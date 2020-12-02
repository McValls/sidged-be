package com.mvalls.sidged.rest.dtos.subject;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public class SubjectUpdateDTO {
	
	private final String name;

	@JsonCreator
	public SubjectUpdateDTO(String name) {
		super();
		this.name = name;
	}
	
}
