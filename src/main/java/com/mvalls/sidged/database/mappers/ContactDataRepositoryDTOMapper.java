package com.mvalls.sidged.database.mappers;

import java.util.ArrayList;

import com.mvalls.sidged.core.model.ContactData;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.ContactDataDTO;

public class ContactDataRepositoryDTOMapper implements RepositoryDTOMapper<ContactData, ContactDataDTO> {

	@Override
	public ContactData dtoToModel(ContactDataDTO dto) {
		return ContactData.builder()
				.id(dto.getId())
				.emails(new ArrayList<>(dto.getEmails()))
				.phones(new ArrayList<>(dto.getPhones()))
				.build();
	}

	@Override
	public ContactDataDTO modelToDto(ContactData model) {
		return ContactDataDTO.builder()
				.id(model.getId())
				.emails(new ArrayList<>(model.getEmails()))
				.phones(new ArrayList<>(model.getPhones()))
				.build();
	}

}
