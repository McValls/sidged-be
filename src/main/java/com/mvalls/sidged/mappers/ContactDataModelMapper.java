package com.mvalls.sidged.mappers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.ContactData;
import com.mvalls.sidged.rest.dtos.ContactDataDTO;

@Component
public class ContactDataModelMapper extends GenericMapper<ContactDataDTO, ContactData>{

	@Override
	public ContactData map(ContactDataDTO dto) {
		return ContactData.builder()
				.emails(new ArrayList<>(dto.getEmails()))
				.phones(new ArrayList<>(dto.getPhones()))
				.build();
	}
	
}
