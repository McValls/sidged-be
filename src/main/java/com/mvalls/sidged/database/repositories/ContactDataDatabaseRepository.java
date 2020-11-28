package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.ContactData;
import com.mvalls.sidged.core.repositories.ContactDataRepository;
import com.mvalls.sidged.database.dtos.ContactDataDTO;
import com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper;
import com.mvalls.sidged.database.repositories.mappers.ContactDataRepositoryDTOMapper;

public class ContactDataDatabaseRepository implements ContactDataRepository {

	private final ContactDataMapper contactDataMapper;
	private final ContactDataRepositoryDTOMapper dtoMapper = new ContactDataRepositoryDTOMapper();
	
	public ContactDataDatabaseRepository(ContactDataMapper contactDataMapper) {
		super();
		this.contactDataMapper = contactDataMapper;
	}

	@Override
	public ContactData create(ContactData contactData) {
		ContactDataDTO dto = this.dtoMapper.modelToDto(contactData);
		this.contactDataMapper.insert(dto);
		contactData.setId(dto.getId());
		return contactData;
	}

	@Override
	public ContactData update(ContactData contactData) {
		ContactDataDTO dto = this.dtoMapper.modelToDto(contactData);
		this.contactDataMapper.update(dto);
		return contactData;
	}

}
