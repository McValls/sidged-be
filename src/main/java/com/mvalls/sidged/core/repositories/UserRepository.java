package com.mvalls.sidged.core.repositories;

import java.util.Optional;

import com.mvalls.sidged.core.model.users.User;

public interface UserRepository {

	Optional<User> findByUserName(String username);
	User create(User user);
	User update(User user);
}
