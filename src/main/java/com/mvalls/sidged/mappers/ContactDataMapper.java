package com.mvalls.sidged.mappers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.ContactData;
import com.mvalls.sidged.rest.dtos.ContactDataDTO;

@Component
public class ContactDataMapper extends GenericMapper<ContactData, ContactDataDTO> {

	@Override
	public ContactDataDTO map(ContactData contactData) {
		return ContactDataDTO.builder()
				.emails(new ArrayList<>(contactData.getEmails()))
				.phones(new ArrayList<>(contactData.getPhones()))
				.build();
	}
	
}
