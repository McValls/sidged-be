package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.rest.dtos.SignUpRequestDTO;

@Component
public class SignUpModelMapper extends GenericMapper<SignUpRequestDTO, User>{

	@Override
	public User map(SignUpRequestDTO dto) {
		return User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.email(dto.getEmail())
				.userType(dto.getUserType())
				.build();
	}
	
}
