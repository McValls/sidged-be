package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.Teacher;

public interface TeacherRepository {

	List<Teacher> findByCourseCode(String courseCode);
	void removeCourseTeacher(String courseCode, Long teacherId);
	void addCourseTeacher(String courseCode, Long teacherId);
	List<Teacher> findAll();
	Teacher create(Teacher teacher);
	Teacher update(Teacher teacher);
}
