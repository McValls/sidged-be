package com.mvalls.sidged.database.repositories.mappers;

import java.util.ArrayList;
import java.util.Arrays;

import com.mvalls.sidged.core.model.ContactData;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.ContactDataDTO;

public class ContactDataRepositoryDTOMapper implements RepositoryDTOMapper<ContactData, ContactDataDTO> {

	@Override
	public ContactData dtoToModel(ContactDataDTO dto) {
		return ContactData.builder()
				.id(dto.getId())
				.emails(dto.getEmails() != null? Arrays.asList(dto.getEmails().split(",")) : new ArrayList<>())
				.phones(dto.getPhones() != null? Arrays.asList(dto.getPhones().split(",")) : new ArrayList<>())
				.build();
	}

	@Override
	public ContactDataDTO modelToDto(ContactData model) {
		return ContactDataDTO.builder()
				.id(model.getId())
				.emails(String.join(",", model.getEmails()))
				.phones(String.join(",", model.getPhones()))
				.build();
	}

}
