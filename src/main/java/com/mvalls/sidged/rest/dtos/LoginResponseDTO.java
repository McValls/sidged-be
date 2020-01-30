package com.mvalls.sidged.rest.dtos;

import java.time.LocalDateTime;

import com.mvalls.sidged.login.UserStatus;
import com.mvalls.sidged.login.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO extends DataTransferObject{
	
	private static final long serialVersionUID = 1160962680454164412L;
	
	private String username;
	private String fullName;
	private LocalDateTime lastTimeLoggedIn;
	private UserStatus userStatus;
	private UserType userType;

}
