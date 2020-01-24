package com.mvalls.sidged.strategies.actions.teachers;

import java.util.Collection;

import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

public class AddTeacherUpdateActionStrategy implements UpdateActionStrategy<Teacher> {

	@Override
	public Collection<Teacher> execute(Teacher teacher, Collection<Teacher> collection) {
		if(collection.add(teacher)) {
			return collection;
		} else {
			throw new UnsupportedOperationException("Couldn't add teacher to the given collection");
		}
	}
	
}
