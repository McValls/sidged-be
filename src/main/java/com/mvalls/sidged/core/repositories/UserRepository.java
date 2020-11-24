package com.mvalls.sidged.core.repositories;

import java.util.Optional;

import com.mvalls.sidged.core.model.users.User;

public interface UserRepository extends GenericRepository<User, Long>{

	Optional<User> findByUserName(String username);
}
