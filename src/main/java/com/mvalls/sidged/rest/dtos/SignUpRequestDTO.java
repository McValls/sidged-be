package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.login.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@NoArgsConstructor
@AllArgsConstructor
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
