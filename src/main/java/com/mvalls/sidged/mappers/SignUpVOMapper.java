package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.rest.dtos.SignUpRequestDTO;
import com.mvalls.sidged.valueObjects.SignUpVO;

@Component
public class SignUpVOMapper extends GenericMapper<SignUpRequestDTO, SignUpVO>{

	@Override
	public SignUpVO map(SignUpRequestDTO dto) {
		return SignUpVO.builder()
			.names(dto.getNames())
			.lastname(dto.getLastname())
			.identificationNumber(dto.getIdentificationNumber())
			.email(dto.getEmail())
			.build();
	}
	
}
