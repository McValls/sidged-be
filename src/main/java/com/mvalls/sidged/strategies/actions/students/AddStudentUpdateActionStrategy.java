package com.mvalls.sidged.strategies.actions.students;

import java.util.Collection;

import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

public class AddStudentUpdateActionStrategy implements UpdateActionStrategy<Student>{

	@Override
	public Collection<Student> execute(Student student, Collection<Student> collection) {
		if(collection.add(student)) {
			return collection;
		} else {
			throw new UnsupportedOperationException("Couldn't add student to the given collection");
		}
	}
	
}
