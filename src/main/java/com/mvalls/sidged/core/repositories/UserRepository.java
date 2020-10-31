package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.users.User;

public interface UserRepository extends GenericRepository<User, Long>{

	User getUserByUsername(String username);

}
