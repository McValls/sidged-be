package com.mvalls.sidged.strategies.actions.teachers;

import java.util.Collection;

import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

public class RemoveTeacherUpdateActionStrategy implements UpdateActionStrategy<Teacher> {

	@Override
	public Collection<Teacher> execute(Teacher teacher, Collection<Teacher> collection) {
		collection.remove(teacher);
		return collection;
	}
	
}
