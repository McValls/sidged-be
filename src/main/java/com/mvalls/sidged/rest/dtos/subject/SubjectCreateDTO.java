package com.mvalls.sidged.rest.dtos.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubjectCreateDTO {
	
	private final String code;
	private final String name;
	private final String careerCode;

}
