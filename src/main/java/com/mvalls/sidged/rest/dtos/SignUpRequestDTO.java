package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.login.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpRequestDTO extends DataTransferObject{

	private static final long serialVersionUID = -8986135386183694028L;

	private String username;
	private String password;
	private UserType userType;
	private String names;
	private String lastname;
	private String identificationNumber;
	private String email;
	
}
