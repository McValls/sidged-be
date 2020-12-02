package com.mvalls.sidged.rest.dtos.subject;

import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.rest.dtos.DataTransferObject;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class SubjectFindAllResponseDTO extends DataTransferObject {
	
	private String code;
	private String name;
	private String careerName;
	private String careerCode;
	
	private SubjectFindAllResponseDTO(String code, String name, String careerName, String careerCode) {
		super();
		this.code = code;
		this.name = name;
		this.careerName = careerName;
		this.careerCode = careerCode;
	}
	
	public static SubjectFindAllResponseDTO build(Subject subject) {
		return new SubjectFindAllResponseDTO(subject.getCode(), 
				subject.getName(), 
				subject.getCareer().getName(), 
				subject.getCareer().getCode());
	}



}
