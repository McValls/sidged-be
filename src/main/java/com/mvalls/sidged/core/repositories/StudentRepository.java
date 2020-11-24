package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Student;

public interface StudentRepository extends GenericRepository<Student, Long> {

	List<Student> findByCourseCode(String courseCode);

	void removeCourseStudent(String courseCode, Long studentId);

	void addCourseStudent(String courseCode, Long studentId);

}
