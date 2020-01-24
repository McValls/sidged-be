package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	

}
