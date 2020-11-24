package com.mvalls.sidged.database.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.ContactData;
import com.mvalls.sidged.core.repositories.ContactDataRepository;
import com.mvalls.sidged.database.mappers.ContactDataMyBatisRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.ContactDataMyBatisDTO;
import com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper;

public class ContactDataDatabaseRepository implements ContactDataRepository {

	private final ContactDataMapper contactDataMapper;
	private final ContactDataMyBatisRepositoryDTOMapper dtoMapper = new ContactDataMyBatisRepositoryDTOMapper();
	
	public ContactDataDatabaseRepository(ContactDataMapper contactDataMapper) {
		super();
		this.contactDataMapper = contactDataMapper;
	}

	@Override
	public ContactData create(ContactData contactData) {
		ContactDataMyBatisDTO dto = this.dtoMapper.modelToDto(contactData);
		this.contactDataMapper.insert(dto);
		contactData.setId(dto.getId());
		return contactData;
	}

	@Override
	public ContactData update(ContactData contactData) {
		ContactDataMyBatisDTO dto = this.dtoMapper.modelToDto(contactData);
		this.contactDataMapper.update(dto);
		return contactData;
	}

	@Override
	public void delete(ContactDataMyBatisDTO obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContactData findById(ContactDataMyBatisDTO id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactData> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
