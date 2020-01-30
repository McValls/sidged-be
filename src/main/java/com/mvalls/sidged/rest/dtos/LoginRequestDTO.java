package com.mvalls.sidged.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO extends DataTransferObject {

	private static final long serialVersionUID = 3527944501278243051L;
	
	private String username;
	private String password;

}
