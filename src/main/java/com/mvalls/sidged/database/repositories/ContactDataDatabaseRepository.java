package com.mvalls.sidged.database.repositories;

import java.util.List;

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

	@Override
	public void delete(ContactDataDTO obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContactData findById(ContactDataDTO id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactData> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
