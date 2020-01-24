package com.mvalls.sidged.valueObjects;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignUpVO {

	private String names;
	private String lastname;
	private String identificationNumber;
	private String email;
	
}
