package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Student;

public interface StudentRepository {

	List<Student> findByCourseCode(String courseCode);
	
	List<Student> findAll();

	void removeCourseStudent(String courseCode, Long studentId);

	void addCourseStudent(String courseCode, Long studentId);

	Student create(Student student);
	
	Student update(Student student);
	
	void delete(Long id);
}
