package com.mvalls.sidged.rest.dtos.correlativity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mvalls.sidged.rest.dtos.DataTransferObject;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class CorrelativityUpdateRequestDTO extends DataTransferObject {
	
	private final List<CorrelativityActionSubjectDTO> updates;

	@JsonCreator
	public CorrelativityUpdateRequestDTO(List<CorrelativityActionSubjectDTO> updates) {
		super();
		this.updates = updates;
	}
}
