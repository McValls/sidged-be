package com.mvalls.sidged.rest.dtos.correlativity;

import com.mvalls.sidged.rest.dtos.DataTransferObject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@Getter
@RequiredArgsConstructor
public class CorrelativityActionSubjectDTO extends DataTransferObject {

	private final CorrelativityAction action;
	private final String subjectCode;
	
}
