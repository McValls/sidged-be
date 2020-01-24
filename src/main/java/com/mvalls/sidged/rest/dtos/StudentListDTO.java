package com.mvalls.sidged.rest.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class StudentListDTO extends DataTransferObject {

	private static final long serialVersionUID = -5666835218981827849L;
	
	private String names;
	private String lastname;
	private String base64EncodedPic;

}
