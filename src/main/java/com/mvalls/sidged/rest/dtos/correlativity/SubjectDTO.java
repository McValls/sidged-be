package com.mvalls.sidged.rest.dtos.correlativity;

import com.mvalls.sidged.rest.dtos.DataTransferObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class SubjectDTO extends DataTransferObject {

	private final String code;
	private final String name;
	
	
	
}
