package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.StudentLink;

public interface StudentLinkRepository extends JpaRepository<StudentLink, Long>{
	
}
