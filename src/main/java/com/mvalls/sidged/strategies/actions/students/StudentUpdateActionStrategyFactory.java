package com.mvalls.sidged.strategies.actions.students;

import com.mvalls.sidged.enums.UpdateAction;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

public class StudentUpdateActionStrategyFactory {

	private StudentUpdateActionStrategyFactory() {}
	
	public static UpdateActionStrategy<Student> getInstance(UpdateAction updateAction) throws IllegalArgumentException {
		switch(updateAction) {
		case ADD:
			return new AddStudentUpdateActionStrategy();
		case REMOVE:
			return new RemoveStudentUpdateActionStrategy();
		default:
			throw new IllegalArgumentException("No strategy for student fouded for action: " + updateAction);
		}
	}
	
}
