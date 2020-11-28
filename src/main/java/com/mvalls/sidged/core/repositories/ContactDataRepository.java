package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.ContactData;

public interface ContactDataRepository {
	
	ContactData create(ContactData contactData);
	ContactData update(ContactData contactData);

}
