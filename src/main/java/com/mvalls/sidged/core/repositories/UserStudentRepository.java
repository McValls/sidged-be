package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.users.UserStudent;

public interface UserStudentRepository extends GenericRepository<UserStudent, Long> {

	UserStudent findByUserUsername(String username);

	UserStudent findByStudentId(Long id);

}
