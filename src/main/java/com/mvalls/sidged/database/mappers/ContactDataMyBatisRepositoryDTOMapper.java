package com.mvalls.sidged.database.mappers;

import java.util.ArrayList;
import java.util.Arrays;

import com.mvalls.sidged.core.model.ContactData;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.ContactDataMyBatisDTO;

public class ContactDataMyBatisRepositoryDTOMapper implements RepositoryDTOMapper<ContactData, ContactDataMyBatisDTO> {

	@Override
	public ContactData dtoToModel(ContactDataMyBatisDTO dto) {
		return ContactData.builder()
				.id(dto.getId())
				.emails(dto.getEmails() != null? Arrays.asList(dto.getEmails().split(",")) : new ArrayList<>())
				.phones(dto.getPhones() != null? Arrays.asList(dto.getPhones().split(",")) : new ArrayList<>())
				.build();
	}

	@Override
	public ContactDataMyBatisDTO modelToDto(ContactData model) {
		return ContactDataMyBatisDTO.builder()
				.id(model.getId())
				.emails(String.join(",", model.getEmails()))
				.phones(String.join(",", model.getPhones()))
				.build();
	}

}
