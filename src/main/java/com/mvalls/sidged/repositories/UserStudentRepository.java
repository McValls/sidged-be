package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.login.UserStudent;

public interface UserStudentRepository extends JpaRepository<UserStudent, Long>{
	
	UserStudent findByStudentId(Long studentId);

	UserStudent findByUserUsername(String username);
	
}
