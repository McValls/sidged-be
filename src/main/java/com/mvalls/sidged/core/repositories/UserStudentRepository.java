package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.users.UserStudent;

public interface UserStudentRepository {

	UserStudent findByUserUsername(String username);

	UserStudent findByStudentId(Long id);
	
	UserStudent create(UserStudent userStudent);
	
	UserStudent update(UserStudent userStudent);

}
