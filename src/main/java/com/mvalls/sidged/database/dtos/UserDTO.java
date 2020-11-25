package com.mvalls.sidged.database.dtos;

import java.time.LocalDateTime;

import com.mvalls.sidged.core.model.users.UserStatus;
import com.mvalls.sidged.core.model.users.UserType;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements RepositoryDTO {

	private Long id;
	private String username;
	private String password;
	private String email;
	private UserType userType;
	private UserStatus userStatus;
	private LocalDateTime lastTimeLogged;
	private LocalDateTime lastTimeModified;
	
}
