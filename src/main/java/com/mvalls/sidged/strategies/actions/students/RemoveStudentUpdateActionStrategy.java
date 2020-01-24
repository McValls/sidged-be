package com.mvalls.sidged.strategies.actions.students;

import java.util.Collection;

import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

public class RemoveStudentUpdateActionStrategy implements UpdateActionStrategy<Student>{

	@Override
	public Collection<Student> execute(Student student, Collection<Student> collection) {
		collection.remove(student);
		return collection;
	}
	
}
