package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.login.UserTeacher;

public interface UserTeacherRepository extends JpaRepository<UserTeacher, Long>{

	UserTeacher findByTeacherId(Long teacherId);
	
	UserTeacher findByUserUsername(String username);
	
}
