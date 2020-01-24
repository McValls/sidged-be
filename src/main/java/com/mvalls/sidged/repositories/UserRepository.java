package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.login.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User getUserByUsername(String username);
	
}
