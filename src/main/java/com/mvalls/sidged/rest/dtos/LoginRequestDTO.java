package com.mvalls.sidged.rest.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequestDTO extends DataTransferObject {

	private static final long serialVersionUID = 3527944501278243051L;
	
	private String username;
	private String password;

}
